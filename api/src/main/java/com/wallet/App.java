package com.wallet;

import com.wallet.handler.CORSHandler;
import ratpack.server.RatpackServer;

public class App {

    public static void main(String[] args) throws Exception {
        RatpackServer.start(ratpackServerSpec -> ratpackServerSpec
            .handlers(chain -> chain
                .all(new CORSHandler())
                .post("login", ctx -> ctx.render("Login"))
                .get("transactions", ctx -> ctx.render("Transactions"))
                .get("balance", ctx -> ctx.render("Balance"))
                .post("spend", ctx -> ctx.render("Spend"))

            ));
    }
}