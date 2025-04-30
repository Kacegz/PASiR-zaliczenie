package com.example.zaliczenie.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.index.Indexed;

@Document(collection = "users")
@Getter
@Setter
public class User {
    @Id
    @Indexed(unique = true)
    private String id;
    @Indexed(unique = true)
    private String username;
    private String password;
    private String role;
}