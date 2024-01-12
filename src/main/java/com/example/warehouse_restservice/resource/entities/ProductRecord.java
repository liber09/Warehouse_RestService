package com.example.warehouse_restservice.resource.entities;

import jakarta.validation.constraints.*;

import java.time.LocalDate;
import java.util.UUID;

public record ProductRecord(@NotNull UUID id, @NotEmpty String name,
                            @NotNull Category category, @Positive @Min(1) @Max(10) int rating,
                            @PastOrPresent LocalDate creationDate, @PastOrPresent LocalDate modifiedDate) {

    public static ProductRecord createProduct(UUID id, String name, Category category, int rating) {
        LocalDate date = LocalDate.now();
        return new ProductRecord(
                id,
                name,
                category,
                rating,
                date,
                date
        );
    }
}
