package com.wallet.handler;

import com.google.gson.Gson;
import com.wallet.authorization.JwtTokenUtil;
import com.wallet.model.Transaction;
import com.wallet.storage.RedisPool;
import ratpack.handling.Context;
import ratpack.handling.InjectionHandler;
import ratpack.http.Request;
import ratpack.http.Response;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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
                    String bearerToken = request.getHeaders().get("Authorization");
                    final String token = bearerToken.replace("Bearer ", "");

                    Set<String> transactionsSet = RedisPool.smembers("transactions#" + JwtTokenUtil.getUidFromToken(token));
                    List<Transaction> transactionsList = new ArrayList<>();
                    for (String transactionStr : transactionsSet) {
                        Transaction transaction = gson.fromJson(transactionStr, Transaction.class);
                        transactionsList.add(transaction);
                    }

                    ctx.render(json(transactionsList));
                })
        );
    }
}