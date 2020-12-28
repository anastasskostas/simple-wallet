package com.wallet.handler;

import ratpack.handling.Context;
import ratpack.handling.InjectionHandler;

public class AccountHandler extends InjectionHandler {

    public void handle(Context ctx, String base) throws Exception {

        ctx.byMethod(byMethodSpec -> byMethodSpec
                .get(() -> {
                    ctx.render("Balance");
                })
                .post(() -> {
                    ctx.render("Spend");
                })
        );
    }
}