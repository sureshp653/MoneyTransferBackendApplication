package com.money.transfer.api.configuration.module;

import com.money.transfer.api.controller.AccountController;
import com.money.transfer.api.controller.TransactionController;
import com.money.transfer.api.controller.context.ContextWrapper;
import com.money.transfer.api.controller.context.DefaultContextWrapper;
import com.money.transfer.api.repository.AccountRepository;
import com.money.transfer.api.repository.TransactionRepository;
import dagger.Module;
import dagger.Provides;

import javax.inject.Inject;
import javax.inject.Singleton;

@Module
public class ControllerModule {
    @Provides
    @Singleton
    public ContextWrapper provideContextWrapper() {
        return new DefaultContextWrapper();
    }

    @Inject
    @Provides
    @Singleton
    public AccountController provideAccountController(
            final ContextWrapper contextWrapper,
            final AccountRepository accountRepository
    ) {
        return new AccountController(contextWrapper, accountRepository);
    }

    @Inject
    @Provides
    @Singleton
    public TransactionController provideTransactionController(
            final ContextWrapper contextWrapper,
            final TransactionRepository transactionRepository,
            final AccountRepository accountRepository
    ) {
        return new TransactionController(contextWrapper, transactionRepository, accountRepository);
    }
}
