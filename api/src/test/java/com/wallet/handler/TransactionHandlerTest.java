package com.wallet.handler;

import com.wallet.App;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import ratpack.test.MainClassApplicationUnderTest;
import ratpack.test.http.TestHttpClient;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TransactionHandlerTest {
    private static MainClassApplicationUnderTest applicationUnderTest = new MainClassApplicationUnderTest(App.class);
    private final TestHttpClient httpClient = applicationUnderTest.getHttpClient();

    @Test
    public void testGetTransactionsWithNoAuth() {
        assertEquals("Invalid token. Please login again.",
                httpClient.getText("/transactions"));

    }

    @AfterAll
    public static void shutdown() {
        applicationUnderTest.close();
    }
}
