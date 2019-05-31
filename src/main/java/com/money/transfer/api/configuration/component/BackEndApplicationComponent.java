package com.money.transfer.api.configuration.component;

import com.money.transfer.api.configuration.module.ControllerModule;
import com.money.transfer.api.configuration.module.RepositoryModule;
import com.money.transfer.api.configuration.module.ValidationModule;
import com.money.transfer.api.controller.AccountController;
import com.money.transfer.api.controller.TransactionController;
import dagger.Component;

import javax.inject.Singleton;

@Singleton
@Component(modules = {
        ControllerModule.class,
        RepositoryModule.class,
        ValidationModule.class,
})
public interface BackEndApplicationComponent {
    AccountController accountController();

    TransactionController transactionController();
}
