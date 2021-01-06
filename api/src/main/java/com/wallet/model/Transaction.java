package com.wallet.model;


import java.math.BigDecimal;
import java.util.Date;

public class Transaction {

    private final Date date;
    private final String description;
    private final BigDecimal amount;
    private final String currency;
    private String type;

    public Transaction(Date date, String description, BigDecimal amount, String currency, String type) {
        this.date = date;
        this.description = description;
        this.amount = amount;
        this.currency = currency;
        this.type = type;
    }

    public Date getDate() {
        return date;
    }

    public String getDescription() {
        return description;
    }

    public String getCurrency() {
        return currency;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
