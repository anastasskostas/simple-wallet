package com.wallet.model;

public class User {

    private final String uid;
    private Integer balance;
    private final String currency;

    //User class constructor
    public User(String uid, Integer balance, String currency) {
        this.uid = uid;
        this.balance = balance;
        this.currency = currency;
    }

    public String getUid() {
        return uid;
    }

    public Integer getBalance() {
        return balance;
    }

    public void setBalance(Integer balance) {
        this.balance = balance;
    }

    public String getCurrency() {
        return currency;
    }
}
