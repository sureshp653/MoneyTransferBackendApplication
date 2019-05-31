package com.money.transfer.api.configuration.module;

import com.money.transfer.api.repository.AccountRepository;
import com.money.transfer.api.validation.AccountValidation;
import com.money.transfer.api.validation.AccountValidationImpl;
import com.money.transfer.api.validation.TransactionValidation;
import com.money.transfer.api.validation.TransactionValidationImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static com.google.common.truth.Truth.assertThat;

@RunWith(MockitoJUnitRunner.class)

public class ValidationModuleTest {
    private ValidationModule validationModule = new ValidationModule();

    @Mock
    private AccountRepository accountRepository;

    @Test
    public void shouldProvideTransactionValidation() {
        // when
        TransactionValidation validation = validationModule.provideTransactionValidation(
                accountRepository
        );

        // then
        assertThat(validation).isNotNull();
        assertThat(validation).isInstanceOf(TransactionValidationImpl.class);
    }

    @Test
    public void shouldProvideAccountValidation() {
        // when
        AccountValidation validation = validationModule.provideAccountValidation();

        // then
        assertThat(validation).isNotNull();
        assertThat(validation).isInstanceOf(AccountValidationImpl.class);
    }
}


