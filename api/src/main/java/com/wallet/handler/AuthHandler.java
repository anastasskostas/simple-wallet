package com.wallet.handler;

import ratpack.handling.Context;
import ratpack.handling.InjectionHandler;
import ratpack.http.Response;

public class AuthHandler extends InjectionHandler {

    public void handle(Context ctx, String base) throws Exception {
        Response response = ctx.getResponse();

        ctx.byMethod(byMethodSpec -> byMethodSpec
                .options(() -> {
                    response.getHeaders().set("Access-Control-Allow-Methods", "OPTIONS, GET, POST, DELETE");
                    response.send();
                })
                .post(() -> {
                    ctx.render("Login");
                })
        );
    }
}