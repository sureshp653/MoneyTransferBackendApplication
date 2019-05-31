package com.money.transfer.api.validation;

import com.money.transfer.api.exception.AccountNotExistsException;
import com.money.transfer.api.exception.DifferentCurrencyException;
import com.money.transfer.api.exception.TransferToTheSameAccountException;
import com.money.transfer.api.model.Transaction;
import com.money.transfer.api.repository.AccountRepository;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class TransactionValidationImpl implements TransactionValidation {

    private AccountRepository accountRepository;

    @Inject
    public TransactionValidationImpl(final AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public Optional<Exception> validate(final Transaction transaction) {
        return createCommitValidationRules(transaction)
                .entrySet()
                .stream()
                .filter(entry -> entry.getKey().equals(Boolean.TRUE))
                .findFirst()
                .map(Map.Entry::getValue);
    }

    private Map<Boolean, Exception> createCommitValidationRules(final Transaction transaction) {
        final Map<Boolean, Exception> rules = new HashMap<>();

        rules.put(
                accountRepository.get(transaction.from().number()).isEmpty(),
                new AccountNotExistsException(transaction.from().number())
        );

        rules.put(
                accountRepository.get(transaction.to().number()).isEmpty(),
                new AccountNotExistsException(transaction.to().number())
        );

        rules.put(
                !transaction.from().money().isSameCurrency(transaction.to().money()),
                new DifferentCurrencyException(transaction.from().number(), transaction.to().number())
        );

        rules.put(
                transaction.from().number().equals(transaction.to().number()),
                new TransferToTheSameAccountException()
        );

        return rules;
    }
}