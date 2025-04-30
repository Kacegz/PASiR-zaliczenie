package com.example.zaliczenie.model;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Document(collection = "ratings")
@Getter
@Setter
public class Rating {
    @Id
    private String id;
    private String teaId;
    private String userId;
    @NotBlank(message = "Must be between 1 and 5")
    @Min(1)
    @Max(5)
    private int score;
}