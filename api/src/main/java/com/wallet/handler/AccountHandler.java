package com.wallet.handler;

import com.google.gson.Gson;
import com.wallet.authorization.JwtTokenUtil;
import com.wallet.model.Transaction;
import com.wallet.model.User;
import com.wallet.storage.RedisPool;
import ratpack.handling.Context;
import ratpack.handling.InjectionHandler;
import ratpack.http.Request;
import ratpack.http.Response;

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
                    final String bearerToken = request.getHeaders().get("Authorization");
                    if (Objects.isNull(bearerToken) && bearerToken.isEmpty() && !bearerToken.startsWith("Bearer ")) {
                        return;
                    }
                    final String token = bearerToken.replace("Bearer ", "");

                    final User user = gson.fromJson(RedisPool.get("user#" + JwtTokenUtil.getUidFromToken(token)), User.class);
                    ctx.render(json(user));
                })
                .post(() -> {
                    request.getBody().then(data -> {
                        final String transaction = data.getText();

                        final String bearerToken = request.getHeaders().get("Authorization");
                        if (Objects.isNull(bearerToken) && bearerToken.isEmpty() && !bearerToken.startsWith("Bearer ")) {
                            return;
                        }
                        final String token = bearerToken.replace("Bearer ", "");

                        final String uid = JwtTokenUtil.getUidFromToken(token);

                        final User user = gson.fromJson(RedisPool.get("user#" + uid), User.class);
                        final Transaction newTransaction = gson.fromJson(transaction, Transaction.class);

                        if (user.getBalance().compareTo(newTransaction.getAmount()) == -1) {
                            return;
                        }

                        user.setBalance(user.getBalance().subtract(newTransaction.getAmount()));

                        RedisPool.set("user#" + uid, gson.toJson(user));
                        RedisPool.sadd("transactions#" + uid, transaction);

                        ctx.render(json("Success"));
                    });
                })
        );
    }
}