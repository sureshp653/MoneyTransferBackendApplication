package com.money.transfer.api.configuration.module;

import com.money.transfer.api.repository.AccountRepository;
import com.money.transfer.api.validation.AccountValidation;
import com.money.transfer.api.validation.AccountValidationImpl;
import com.money.transfer.api.validation.TransactionValidation;
import com.money.transfer.api.validation.TransactionValidationImpl;
import dagger.Module;
import dagger.Provides;

import javax.inject.Inject;
import javax.inject.Singleton;

@Module
public class ValidationModule {
    @Inject
    @Provides
    @Singleton
    TransactionValidation provideTransactionValidation(final AccountRepository accountRepository) {
        return new TransactionValidationImpl(accountRepository);
    }

    @Provides
    @Singleton
    AccountValidation provideAccountValidation() {
        return new AccountValidationImpl();
    }
}
