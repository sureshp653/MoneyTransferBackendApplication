package com.money.transfer.api.model;



import java.util.Objects;

@Data
@AllArgsConstructor
@Builder
public class User {
    private final String id;
    private final String name;
    private final String surname;    
}
