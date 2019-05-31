package com.money.transfer.api.validation;

import com.money.transfer.api.exception.EmptyAccountNumberException;
import com.money.transfer.api.exception.EmptyUserIdException;
import com.money.transfer.api.exception.EmptyUserNameException;
import com.money.transfer.api.exception.EmptyUserSurnameException;
import com.money.transfer.api.model.Account;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class AccountValidationImpl implements AccountValidation {

    @Override public Optional<Exception> validate(final Account account) {
        return createAccountValidationRules(account)
                .entrySet()
                .stream()
                .filter(entry -> entry.getKey().equals(Boolean.TRUE))
                .findFirst()
                .map(Map.Entry::getValue);
    }

    private Map<Boolean, Exception> createAccountValidationRules(final Account account) {
        final Map<Boolean, Exception> rules = new HashMap<>();

        rules.put(
                account.number() == null || account.number().isEmpty() || account.number().isBlank(),
                new EmptyAccountNumberException()
        );

        rules.put(
                account.user().id() == null || account.user().id().isEmpty(),
                new EmptyUserIdException()
        );

        rules.put(
                account.user().name() == null || account.user().name().isEmpty() || account.user().name().isBlank(),
                new EmptyUserNameException()
        );

        rules.put(
                account.user().surname() == null || account.user().surname().isEmpty() || account.user().surname().isBlank(),
                new EmptyUserSurnameException()
        );

        return rules;
    }
}