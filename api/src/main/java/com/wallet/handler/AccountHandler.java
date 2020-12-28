package com.wallet.handler;

import ratpack.handling.Context;
import ratpack.handling.InjectionHandler;
import ratpack.http.Response;

public class AccountHandler extends InjectionHandler {

    public void handle(Context ctx, String base) throws Exception {
        Response response = ctx.getResponse();

        ctx.byMethod(byMethodSpec -> byMethodSpec
                .options(() -> {
                    response.getHeaders().set("Access-Control-Allow-Methods", "OPTIONS, GET, POST, DELETE");
                    response.send();
                })
                .get(() -> {
                    ctx.render("Balance");
                })
                .post(() -> {
                    ctx.render("Spend");
                })
        );
    }
}