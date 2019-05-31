package com.money.transfer.api.repository;

import com.money.transfer.api.model.Account;
import org.joda.money.Money;

import java.util.Map;
import java.util.Optional;

public interface AccountRepository {

    Optional<Account> get(String number);

    Map<String, Account> get();

    Account create(Account account) throws Exception;

    Account update(String number, Account account) throws Exception;

    void withdrawMoney(Account account, Money money);

    void putMoney(Account account, Money money);

    void delete(String number);

    void clear();
}
