package com.money.transfer.api.controller;

import com.money.transfer.api.controller.context.ContextWrapper;
import com.money.transfer.api.model.Account;
import com.money.transfer.api.model.Response;
import com.money.transfer.api.model.Transaction;
import com.money.transfer.api.repository.AccountRepository;
import com.money.transfer.api.repository.TransactionRepository;
import io.javalin.Context;

import org.joda.money.Money;

import javax.inject.Inject;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;


public class TransactionController {

    private ContextWrapper contextWrapper;
    private TransactionRepository transactionRepository;
    private AccountRepository accountRepository;

    @Inject
    public TransactionController(
            ContextWrapper contextWrapper,
            TransactionRepository transactionRepository,
            AccountRepository accountRepository
    ) {
        this.contextWrapper = contextWrapper;
        this.transactionRepository = transactionRepository;
        this.accountRepository = accountRepository;
    }

    public void getOne(final Context context) {
        String id = contextWrapper.pathParam(context, "id");
        Optional<Transaction> transaction = transactionRepository.get(id);

        if (transaction.isPresent()) {
            contextWrapper.json(context, transaction.get());
        } else {
            Response response = Response.builder()
                    .message(String.format("transaction with id %s does not exist", id))
                    .build();

            contextWrapper.json(context, response, 404);
        }
    }

    public void getAll(final Context context) {
        contextWrapper.json(context, transactionRepository.get());
    }

    public void commit(final Context context) {
        String from = contextWrapper.formParam(context, "from");
        String to = contextWrapper.formParam(context, "to");
        Optional<Account> senderAccount = accountRepository.get(from);
        Optional<Account> receiverAccount = accountRepository.get(to);

        if (senderAccount.isEmpty() || receiverAccount.isEmpty()) {
            createInvalidAccountResponse(context);
            return;
        }

        Optional<Money> money = parseMoney(context);

        if (money.isEmpty()) {
            createInvalidMoneyFormatResponse(context);
            return;
        }

        commit(context, createTransaction(senderAccount.get(), receiverAccount.get(), money.get()));
    }

    private void createInvalidAccountResponse(Context context) {
        Response response = Response.builder()
                .message("Trying to transfer money from or to account, which does not exist")
                .build();

        contextWrapper.json(context, response);
    }

    private Optional<Money> parseMoney(Context context) {
        try {
            Money money = Money.parse(String.format("%s %s",
                    contextWrapper.formParam(context, "currency"),
                    contextWrapper.formParam(context, "money"))
            );
            return Optional.of(money);
        } catch (Exception exception) {
            return Optional.empty();
        }
    }

    private void createInvalidMoneyFormatResponse(Context context) {
        Response response = Response.builder()
                .message("invalid money format")
                .build();

        contextWrapper.json(context, response);
    }

    private Transaction createTransaction(Account sender, Account receiver, Money money) {
        return Transaction.builder()
                .id(UUID.randomUUID().toString())
                .createdAt(LocalDateTime.now())
                .from(sender)
                .to(receiver)
                .money(money)
                .build();
    }

    private void commit(final Context context, final Transaction transaction) {
        try {
            transactionRepository.commit(transaction);
            Response response = Response.builder()
                    .message("transaction committed")
                    .object(transaction)
                    .build();

            contextWrapper.json(context, response);
        } catch (Exception exception) {
            Response response = Response.builder()
                    .message(exception.getMessage())
                    .build();

            contextWrapper.json(context, response);
        }
    }
}
