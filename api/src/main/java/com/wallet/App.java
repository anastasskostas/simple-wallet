package com.wallet;

import com.wallet.handler.AccountHandler;
import com.wallet.handler.AuthHandler;
import com.wallet.handler.CORSHandler;
import com.wallet.handler.TransactionHandler;
import ratpack.server.RatpackServer;

public class App {

    public static void main(String[] args) throws Exception {
        RatpackServer.start(ratpackServerSpec -> ratpackServerSpec
                .handlers(chain -> chain
                        .all(new CORSHandler())
                        .path("login", new AuthHandler())
                        .path("transactions", new TransactionHandler())
                        .path("balance", new AccountHandler())
                        .path("spend", new AccountHandler())
                ));
    }
}