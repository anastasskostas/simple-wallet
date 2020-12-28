package com.wallet.handler;

import com.wallet.authorization.JwtTokenUtil;
import com.wallet.model.User;
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
                    String uid = UUID.randomUUID().toString();
                    User newUser = new User(uid, 100, "GBP");

                    JwtTokenUtil jwtTokenUtil = new JwtTokenUtil();
                    String token = jwtTokenUtil.generateToken(newUser);

                    Map<String, String> res = new HashMap<>();
                    res.put("token", token);

                    ctx.render(json(res));
                })
        );
    }
}