package com.wallet;

import com.wallet.handler.AccountHandler;
import com.wallet.handler.AuthHandler;
import com.wallet.handler.CORSHandler;
import com.wallet.handler.TransactionHandler;
import com.wallet.storage.RedisPool;
import ratpack.server.RatpackServer;

public class App {

    public static void main(String[] args) throws Exception {

        //Clear redis before starting the application
        RedisPool.getJedis().flushAll();

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