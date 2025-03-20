package com.example.zaliczenie.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "teas")
public class Tea {

    @Id
    private String id;
    @Getter
    @Setter
    private String name;
    @Setter
    @Getter
    private String description;
    @Setter
    @Getter
    private String origin;
    @Setter
    @Getter
    private int stock;
    @Setter
    @Getter
    private String createdBy;
}
