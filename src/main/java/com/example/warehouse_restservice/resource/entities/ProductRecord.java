package com.example.warehouse_restservice.resource.entities;

import java.time.LocalDate;
import java.util.UUID;

public record ProductRecord(UUID id, String name, Category category, int rating, LocalDate creationDate, LocalDate modifiedDate) {

}
