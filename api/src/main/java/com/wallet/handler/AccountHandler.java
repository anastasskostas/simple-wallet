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

import static ratpack.jackson.Jackson.json;

public class AccountHandler extends InjectionHandler {

    public void handle(Context ctx, String base) throws Exception {
        Gson gson = new Gson();
        Response response = ctx.getResponse();
        Request request = ctx.getRequest();

        ctx.byMethod(byMethodSpec -> byMethodSpec
                .options(() -> {
                    response.getHeaders().set("Access-Control-Allow-Methods", "OPTIONS, GET, POST, DELETE");
                    response.send();
                })
                .get(() -> {
                    String bearerToken = request.getHeaders().get("Authorization");
                    String token = bearerToken.replace("Bearer ", "");

                    String uid = JwtTokenUtil.getUidFromToken(token);

                    String userJson = RedisPool.get("user#" + uid);
                    User user = gson.fromJson(userJson, User.class);

                    ctx.render(json(user));
                })
                .post(() -> {
                    request.getBody().then(data -> {
                        String text = data.getText();

                        String bearerToken = request.getHeaders().get("Authorization");
                        String token = bearerToken.replace("Bearer ", "");

                        String uid = JwtTokenUtil.getUidFromToken(token);

                        String userJson = RedisPool.get("user#" + uid);
                        User user = gson.fromJson(userJson, User.class);
                        Transaction newTransaction = gson.fromJson(text, Transaction.class);

                        if (user.getBalance() < newTransaction.getAmount()) {
                            return;
                        }

                        user.setBalance(user.getBalance() - newTransaction.getAmount());
                        userJson = gson.toJson(user);
                        RedisPool.set("user#" + uid, userJson);
                        RedisPool.sadd("transactions#" + uid, text);

                        ctx.render(json("Success"));

                    });
                })
        );
    }
}