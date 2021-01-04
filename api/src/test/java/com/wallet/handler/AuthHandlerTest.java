package com.wallet.handler;

import com.google.gson.Gson;
import com.wallet.App;
import com.wallet.authorization.JwtTokenUtil;
import com.wallet.model.User;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import ratpack.test.MainClassApplicationUnderTest;
import ratpack.test.http.TestHttpClient;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AuthHandlerTest {

    private static MainClassApplicationUnderTest applicationUnderTest = new MainClassApplicationUnderTest(App.class);
    private final TestHttpClient httpClient = applicationUnderTest.getHttpClient();

    private String uid = "";
    private String token = "";
    private User user;
    private Gson gson = new Gson();

    @Test
    public void loginReturnToken() {

        final String uid = UUID.randomUUID().toString();
        final User newUser = new User(uid, new BigDecimal(100), "GBP");

        final String token = JwtTokenUtil.generateToken(newUser);
        final Map<String, String> res = new HashMap<>();
        res.put("token", "Bearer " + token);

        assertEquals(gson.toJson(res).startsWith("Bearer "),
                applicationUnderTest.getHttpClient().postText("/login").startsWith("Bearer "));
    }

    @AfterAll
    public static void shutdown() {
        applicationUnderTest.close();
    }
}
