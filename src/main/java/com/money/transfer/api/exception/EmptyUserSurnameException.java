package com.money.transfer.api.exception;

public class EmptyUserSurnameException extends RuntimeException {
    @Override public String getMessage() {
        return "User surname is empty";
    }
}
