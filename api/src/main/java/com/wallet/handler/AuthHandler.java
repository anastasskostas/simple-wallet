package com.wallet.handler;

import com.google.gson.Gson;
import com.wallet.authorization.JwtTokenUtil;
import com.wallet.model.User;
import com.wallet.storage.RedisPool;
import ratpack.handling.Context;
import ratpack.handling.InjectionHandler;
import ratpack.http.Response;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static ratpack.jackson.Jackson.json;

public class AuthHandler extends InjectionHandler {

    public void handle(Context ctx, String base) throws Exception {
        Response response = ctx.getResponse();

        ctx.byMethod(byMethodSpec -> byMethodSpec
                .options(() -> {
                    response.getHeaders().set("Access-Control-Allow-Methods", "OPTIONS, GET, POST, DELETE");
                    response.send();
                })
                .post(() -> {
                    Gson gson = new Gson();

                    String uid = UUID.randomUUID().toString();
                    User newUser = new User(uid, 100, "GBP");

                    String newUserJson = gson.toJson(newUser);
                    RedisPool.set("user#" + uid, newUserJson);

                    String token = JwtTokenUtil.generateToken(newUser);

                    Map<String, String> res = new HashMap<>();
                    res.put("token", "Bearer " + token);

                    ctx.render(json(res));

                })
        );
    }
}