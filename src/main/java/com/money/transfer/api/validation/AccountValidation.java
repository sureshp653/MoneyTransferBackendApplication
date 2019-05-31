package com.money.transfer.api.validation;

import com.money.transfer.api.model.Account;

import java.util.Optional;

public interface AccountValidation {
    Optional<Exception> validate(Account account);
}
