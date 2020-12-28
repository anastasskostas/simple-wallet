package com.wallet.handler;

import ratpack.handling.Context;
import ratpack.handling.InjectionHandler;

public class TransactionHandler extends InjectionHandler {

    public void handle(Context ctx, String base) throws Exception {

        ctx.byMethod(byMethodSpec -> byMethodSpec
                .get(() -> {
                    ctx.render("Transactions");
                })
        );
    }
}