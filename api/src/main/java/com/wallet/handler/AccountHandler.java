package com.wallet.handler;

import com.google.gson.Gson;
import com.wallet.authorization.JwtTokenUtil;
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
                        String token = request.getHeaders().get("Authorization");
                        JwtTokenUtil jwtTokenUtil = new JwtTokenUtil();
                        String uid = jwtTokenUtil.getUidFromToken(token);

                        String userJson = jedis.get("user#" + uid);
                        User user = gson.fromJson(userJson, User.class);

                        ctx.render(json(user));
                    }
                })
                .post(() -> {
                    ctx.render("Spend");
                })
        );
    }
}