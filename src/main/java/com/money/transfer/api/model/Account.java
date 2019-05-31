package com.money.transfer.api.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.joda.money.Money;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Builder
@AllArgsConstructor
@Data
public class Account {
    private transient final Lock lock;
    private final String number;
    private final User user;
    private Money money;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;   
}
