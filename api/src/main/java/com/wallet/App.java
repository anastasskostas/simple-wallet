package com.wallet;

import com.wallet.handler.AccountHandler;
import com.wallet.handler.AuthHandler;
import com.wallet.handler.CORSHandler;
import com.wallet.handler.TransactionHandler;
import com.wallet.storage.RedisPool;
import ratpack.server.RatpackServer;
import redis.clients.jedis.Jedis;

public class App {
    private static Jedis jedis;

    public static void main(String[] args) throws Exception {
        jedis = RedisPool.getJedis();
        jedis.flushAll();

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