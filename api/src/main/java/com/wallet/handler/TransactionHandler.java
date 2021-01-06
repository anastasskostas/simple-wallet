package com.wallet.handler;

import com.google.gson.Gson;
import com.wallet.authorization.JwtTokenUtil;
import com.wallet.exception.WalletException;
import com.wallet.model.Transaction;
import com.wallet.storage.RedisPool;
import com.wallet.util.Constants;
import ratpack.handling.Context;
import ratpack.handling.InjectionHandler;
import ratpack.http.Request;
import ratpack.http.Response;
import ratpack.http.Status;

import java.util.*;

import static ratpack.jackson.Jackson.json;

public class TransactionHandler extends InjectionHandler {
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

                        //Get transactions from redis only if token is valid, otherwise throw 401 exception
                        final Set<String> transactionsSet = RedisPool.smembers("transactions#" + JwtTokenUtil.getUidFromToken(token));
                        final List<Transaction> transactionsList = new ArrayList<>();
                        for (String transactionStr : transactionsSet) {
                            transactionsList.add(gson.fromJson(transactionStr, Transaction.class));
                        }

                        //Sort transactions by date
                        Collections.sort(transactionsList,
                                Comparator.comparing(Transaction::getDate, Comparator.reverseOrder()));

                        ctx.render(json(transactionsList));
                    } catch (WalletException e) {
                        response.status(e.getCode());
                        ctx.render(e.getMessage());
                    }

                })
        );
    }
}