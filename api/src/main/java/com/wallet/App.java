package com.wallet;

import ratpack.server.RatpackServer;

public class App {

    public static void main(String[] args) throws Exception {
        RatpackServer.start(ratpackServerSpec -> ratpackServerSpec
            .handlers(chain -> chain
                .post("login", ctx -> ctx.render("Login"))
                .get("transcations", ctx -> ctx.render("Transactions"))
                .get("balance", ctx -> ctx.render("Balance"))
                .post("spend", ctx -> ctx.render("Spend"))

            ));
    }
}