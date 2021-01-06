package com.wallet.handler;

import com.google.gson.Gson;
import com.wallet.App;
import com.wallet.authorization.JwtTokenUtil;
import com.wallet.exception.WalletException;
import com.wallet.model.User;
import com.wallet.storage.RedisPool;
import io.netty.handler.codec.http.HttpHeaderNames;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ratpack.test.MainClassApplicationUnderTest;
import ratpack.test.http.TestHttpClient;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AccountHandlerTest {
    private static MainClassApplicationUnderTest applicationUnderTest = new MainClassApplicationUnderTest(App.class);
    private final TestHttpClient httpClient = applicationUnderTest.getHttpClient();

    private String uid = "";
    private String token = "";
    private User user;
    private Gson gson = new Gson();

    @BeforeEach
    public void initData() throws WalletException {
        uid = UUID.randomUUID().toString();
        user = new User(uid, "GBP");
        RedisPool.set("user#" + uid, gson.toJson(user));
        token = JwtTokenUtil.generateToken(user);
    }

    @Test
    public void getBalanceWithWrongToken() {
        String result = httpClient.requestSpec(r -> r
                .headers(h -> h
                        .set(HttpHeaderNames.AUTHORIZATION, "Bearer1 " + token))).get("/balance").getBody().getText();

        assertEquals(result, "Invalid token. Please login again.");

    }

    @Test
    public void getBalanceWithCorrectToken() {

        String result = httpClient.requestSpec(r -> r
                .headers(h -> h
                        .set(HttpHeaderNames.AUTHORIZATION, "Bearer " + token))).get("/balance").getBody().getText();
        User localUser = gson.fromJson(result, User.class);

        assertEquals(gson.toJson(user.getUid()), result);
        assertEquals(user.getUid(), localUser.getUid());
        assertEquals(user.getCurrency(), localUser.getCurrency());

    }

    @AfterAll
    public static void shutdown() {
        applicationUnderTest.close();
    }
}
