package com.wallet.handler;

import com.google.gson.Gson;
import com.wallet.authorization.JwtTokenUtil;
import com.wallet.exception.WalletException;
import com.wallet.model.Transaction;
import com.wallet.model.User;
import com.wallet.storage.RedisPool;
import com.wallet.util.Constants;
import ratpack.handling.Context;
import ratpack.handling.InjectionHandler;
import ratpack.http.Response;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static ratpack.jackson.Jackson.json;

public class AuthHandler extends InjectionHandler {
    private final Gson gson = new Gson();

    public void handle(Context ctx, String base) throws Exception {
        Response response = ctx.getResponse();

        ctx.byMethod(byMethodSpec -> byMethodSpec
                .options(() -> {
                    response.getHeaders().set("Access-Control-Allow-Methods", "OPTIONS, GET, POST, DELETE");
                    response.send();
                })
                .post(() -> {
                    try {
                        //Get random uid and create a User and a transaction for deposit
                        final String uid = UUID.randomUUID().toString();
                        final User newUser = new User(uid, "GBP");
                        final Transaction transaction = new Transaction(new Date(), Constants.DEPOSIT, new BigDecimal(100), "GBP", Constants.DEPOSIT);

                        //Save user and transaction to redis
                        RedisPool.set("user#" + uid, gson.toJson(newUser));
                        RedisPool.sadd("transactions#" + uid, gson.toJson(transaction));

                        //Generate token
                        final String token = JwtTokenUtil.generateToken(newUser);

                        //Return token
                        final Map<String, String> res = new HashMap<>();
                        res.put("token", "Bearer " + token);
                        ctx.render(json(res));
                    } catch (WalletException e) {
                        response.status(e.getCode());
                        ctx.render(e.getMessage());
                    }

                })
        );
    }
}