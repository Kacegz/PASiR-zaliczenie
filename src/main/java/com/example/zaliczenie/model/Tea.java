package com.example.zaliczenie.model;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "teas")
@Getter
@Setter
public class Tea {
    @Id
    private String id;
    @NotBlank(message = "Name is required")
    private String name;
    private String description;
    private String origin;
    private String createdBy;
}
