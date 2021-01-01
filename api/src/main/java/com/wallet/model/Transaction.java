package com.wallet.model;


import java.math.BigDecimal;
import java.util.Date;

public class Transaction {

    private final Date date;
    private final String description;
    private final BigDecimal amount;
    private final String currency;

    public Transaction(Date date, String description, BigDecimal amount, String currency) {
        this.date = date;
        this.description = description;
        this.amount = amount;
        this.currency = currency;
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
}
