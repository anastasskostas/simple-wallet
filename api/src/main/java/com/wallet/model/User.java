package com.wallet.model;

import java.math.BigDecimal;

public class User {

    private final String uid;
    private BigDecimal balance;
    private final String currency;

    //User class constructor
    public User(String uid, String currency) {
        this.uid = uid;
        this.balance = BigDecimal.ZERO;
        this.currency = currency;
    }

    public String getUid() {
        return uid;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public String getCurrency() {
        return currency;
    }
}
