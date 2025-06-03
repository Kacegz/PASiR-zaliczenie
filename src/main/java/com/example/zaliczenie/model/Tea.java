package com.example.zaliczenie.model;

import jakarta.validation.constraints.NotNull;
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
    @NotNull(message = "Name is required")
    private String name;
    @NotNull(message = "Description is required")
    private String description;
    @NotNull(message = "Origin is required")
    private String origin;
    @NotNull(message = "Creator is required")
    private String createdBy;
    private double averageRating;
}
