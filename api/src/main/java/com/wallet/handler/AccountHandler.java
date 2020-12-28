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
import redis.clients.jedis.Jedis;

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
                    try (Jedis jedis = RedisPool.getJedis()) {
                        String bearerToken = request.getHeaders().get("Authorization");
                        String token = bearerToken.replace("Bearer ","");
                        JwtTokenUtil jwtTokenUtil = new JwtTokenUtil();
                        String uid = jwtTokenUtil.getUidFromToken(token);

                        String userJson = jedis.get("user#" + uid);
                        User user = gson.fromJson(userJson, User.class);

                        ctx.render(json(user));
                    }
                })
                .post(() -> {
                    request.getBody().then(data -> {
                        try (Jedis jedis = RedisPool.getJedis()) {

                            String text = data.getText();

                            String bearerToken = request.getHeaders().get("Authorization");
                            String token = bearerToken.replace("Bearer ","");
                            JwtTokenUtil jwtTokenUtil = new JwtTokenUtil();
                            String uid = jwtTokenUtil.getUidFromToken(token);

                            String userJson = jedis.get("user#" + uid);
                            User user = gson.fromJson(userJson, User.class);
                            Transaction newTransaction = gson.fromJson(text, Transaction.class);

                            if (user.getBalance() < newTransaction.getAmount()) {
                                return;
                            }

                            user.setBalance(user.getBalance() - newTransaction.getAmount());
                            userJson = gson.toJson(user);
                            RedisPool.set("user#" + uid, userJson);
                            jedis.sadd("transactions#" + uid, text);

                            ctx.render(json("Success"));
                        }

                    });
                })
        );
    }
}