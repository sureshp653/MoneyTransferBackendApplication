package com.money.transfer.api.model;


import java.util.Objects;

@Data
@AllArgsConstructor
@Builder
public class Response {
    private final String message;
    private final Object object;    
}
