package com.money.transfer.api.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.joda.money.Money;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;


@AllArgsConstructor
@Builder
@Data
public class Transaction {
    private final transient AtomicBoolean isRunning;
    private final String id;
    private final Account from;
    private final Account to;
    private final Money money;
    private final LocalDateTime createdAt;    
}
