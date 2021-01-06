package com.wallet.handler;

import com.google.gson.Gson;
import com.wallet.authorization.JwtTokenUtil;
import com.wallet.exception.WalletException;
import com.wallet.model.Transaction;
import com.wallet.model.User;
import com.wallet.storage.RedisPool;
import com.wallet.util.Constants;
import ratpack.handling.Context;
import ratpack.handling.InjectionHandler;
import ratpack.http.Request;
import ratpack.http.Response;
import ratpack.http.Status;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.Set;

import static ratpack.jackson.Jackson.json;

public class AccountHandler extends InjectionHandler {
    private final Gson gson = new Gson();

    public void handle(Context ctx, String base) throws Exception {
        Response response = ctx.getResponse();
        Request request = ctx.getRequest();

        ctx.byMethod(byMethodSpec -> byMethodSpec
                .options(() -> {
                    response.getHeaders().set("Access-Control-Allow-Methods", "OPTIONS, GET, POST, DELETE");
                    response.send();
                })
                .get(() -> {
                    try {
                        //Get token from headers and check if it is in valid format
                        final String bearerToken = request.getHeaders().get("Authorization");
                        if (Objects.isNull(bearerToken) || bearerToken.isEmpty() || !bearerToken.startsWith("Bearer ")) {
                            throw new WalletException(Status.UNAUTHORIZED, Constants.INVALID_TOKEN);
                        }
                        final String token = bearerToken.replace("Bearer ", "");

                        //Validate token and get uid - return user data
                        String uid = JwtTokenUtil.getUidFromToken(token);

                        //Calculate user current balance
                        final Set<String> transactionsSet = RedisPool.smembers("transactions#" + uid);
                        BigDecimal balance = BigDecimal.ZERO;
                        for (String transactionStr : transactionsSet) {
                            Transaction transaction = gson.fromJson(transactionStr, Transaction.class);
                            if (transaction.getType().equals(Constants.DEPOSIT)) {
                                balance = balance.add(transaction.getAmount());
                            } else {
                                balance = balance.subtract(transaction.getAmount());
                            }
                        }
                        final User user = gson.fromJson(RedisPool.get("user#" + uid), User.class);
                        user.setBalance(balance);
                        ctx.render(json(user));
                    } catch (WalletException e) {
                        response.status(e.getCode());
                        ctx.render(e.getMessage());
                    }
                })
                .post(() -> {
                    request.getBody().then(data -> {
                        try {
                            //Get new transaction
                            final String newTransactionStr = data.getText();

                            //Get token from headers and check if it is in valid format
                            final String bearerToken = request.getHeaders().get("Authorization");
                            if (Objects.isNull(bearerToken) || bearerToken.isEmpty() || !bearerToken.startsWith("Bearer ")) {
                                throw new WalletException(Status.UNAUTHORIZED, Constants.INVALID_TOKEN);
                            }
                            final String token = bearerToken.replace("Bearer ", "");

                            //Validate token and get uid - return user data
                            final String uid = JwtTokenUtil.getUidFromToken(token);

                            //Convert transaction string to Transaction object and update type - usually I would set type from UI request
                            final Transaction newTransaction = gson.fromJson(newTransactionStr, Transaction.class);
                            newTransaction.setType(Constants.WITHDRAWAL);

                            //Calculate user current balance
                            final Set<String> transactionsSet = RedisPool.smembers("transactions#" + uid);
                            BigDecimal balance = BigDecimal.ZERO;
                            for (String transactionStr : transactionsSet) {
                                Transaction transaction = gson.fromJson(transactionStr, Transaction.class);
                                if (transaction.getType().equals(Constants.DEPOSIT)) {
                                    balance = balance.add(transaction.getAmount());
                                } else {
                                    balance = balance.subtract(transaction.getAmount());
                                }
                            }

                            //Throw error if new transaction is higher than current balance, else add new transaction
                            if (balance.compareTo(newTransaction.getAmount()) == -1) {
                                throw new WalletException(Status.BAD_REQUEST, Constants.INSUFFICIENT_BALANCE);
                            }
                            RedisPool.sadd("transactions#" + uid, gson.toJson(newTransaction));

                            ctx.render(json(Constants.SUCCESSFUL_TRANSACTION));
                        } catch (WalletException e) {
                            response.status(e.getCode());
                            ctx.render(e.getMessage());
                        }
                    });
                })
        );
    }
}