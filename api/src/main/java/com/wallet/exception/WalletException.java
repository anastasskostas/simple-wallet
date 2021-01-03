package com.wallet.exception;

import ratpack.http.Status;

public class WalletException extends Exception {

    private final Status code;

    public WalletException(Status code, String message) {
        super(message);
        this.code = code;
    }

    public WalletException(Status code,String message, Throwable cause) {
        super(message, cause);
        this.code = code;
    }

    public WalletException(Status code,Throwable cause) {
        super(cause);
        this.code = code;
    }

    public Status getCode() {
        return code;
    }
}
