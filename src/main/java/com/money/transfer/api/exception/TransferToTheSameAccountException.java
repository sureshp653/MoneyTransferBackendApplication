package com.money.transfer.api.exception;

public class TransferToTheSameAccountException extends RuntimeException {
    @Override public String getMessage() {
        return "Sender and receiver account numbers have to be different";
    }
}
