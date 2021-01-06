package com.wallet.util;

public class Constants {
    //Login
    public static final String INVALID_TOKEN = "Invalid token. Please login again.";

    //Redis
    public static final String REDIS_CANNOT_SAVE = "Cannot save data to redis with key ";
    public static final String REDIS_CANNOT_READ = "Cannot read data from redis with key ";

    // Wallet
    public static final String DEPOSIT = "Deposit";
    public static final String WITHDRAWAL = "Withdrawal";
    public static final String INSUFFICIENT_BALANCE = "Insufficient Balance";
    public static final String SUCCESSFUL_TRANSACTION = "Transaction is completed successfully";
}
