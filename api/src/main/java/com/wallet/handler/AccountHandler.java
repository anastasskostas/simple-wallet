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

import java.util.Objects;

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
                        final User user = gson.fromJson(RedisPool.get("user#" + uid), User.class);
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
                            final String transaction = data.getText();

                            //Get token from headers and check if it is in valid format
                            final String bearerToken = request.getHeaders().get("Authorization");
                            if (Objects.isNull(bearerToken) || bearerToken.isEmpty() || !bearerToken.startsWith("Bearer ")) {
                                throw new WalletException(Status.UNAUTHORIZED, Constants.INVALID_TOKEN);
                            }
                            final String token = bearerToken.replace("Bearer ", "");

                            //Validate token and get uid - return user data
                            final String uid = JwtTokenUtil.getUidFromToken(token);

                            //Get user from redis and convert to object
                            final User user = gson.fromJson(RedisPool.get("user#" + uid), User.class);
                            final Transaction newTransaction = gson.fromJson(transaction, Transaction.class);

                            //Throw error if new transaction is higher than current balance, else update user balance
                            if (user.getBalance().compareTo(newTransaction.getAmount()) == -1) {
                                throw new WalletException(Status.BAD_REQUEST, "Insufficient Balance");
                            }
                            user.setBalance(user.getBalance().subtract(newTransaction.getAmount()));

                            //Save user with updated balance and add transaction to the list
                            RedisPool.set("user#" + uid, gson.toJson(user));
                            RedisPool.sadd("transactions#" + uid, transaction);

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