package com.example.warehouse_restservice.resource.entities;

import com.example.warehouse_restservice.resource.entities.helpers.FakeUuidProvider;
import com.example.warehouse_restservice.resource.entities.helpers.RandomUuidProvider;
import com.example.warehouse_restservice.resource.interfaces.IUuidProvider;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.UUID;

public class Product implements Serializable {
    private final UUID id;
    @NotEmpty(message = "A product name must be provided")
    private String name;
    @NotEmpty(message = "Product must belong to a category")
    private Category category;
    @Positive(message = "Rating must be at least 1")
    @Max(message = "Rating cannot be higher than 10", value = 10L)
    private int rating;
    @PastOrPresent(message = "Created date cannot be in the future")
    private LocalDate createdDate;
    private LocalDate modifiedDate;

    public Product() {
        IUuidProvider uuidProvider = new RandomUuidProvider();
        this.id = uuidProvider.uuid();
    }
    public Product(String name, Category category, int rating, LocalDate creationDate, Boolean isTest, int testId) {
        if(!isTest){
            IUuidProvider UuidProvider = new RandomUuidProvider();
            this.id = UuidProvider.uuid();
        }else{
            IUuidProvider IUuidProviderTest = new FakeUuidProvider();
            this.id = IUuidProviderTest.genterateUuid(testId);
        }

        this.name = name;
        this.category = category;
        this.rating = rating;
        this.createdDate = creationDate;
        this.modifiedDate = createdDate;
    }

    public UUID getId(){
        return this.id;
    }

    public void setName(String value){
        if(!value.isEmpty()){
            this.name = value;
        }
    }

    public void setCategory(Category value){
        if(!(value == null)){
            this.category = value;
        }
    }

    public void setRating(int value){
        if (value >= 0 && value <=10){
            this.rating = value;
        }
    }

    public void setModifiedDate(LocalDate value){
        if (value.isAfter(this.createdDate)){
            this.modifiedDate = value;
        } else {
            System.out.println("Modified date cannot be before creation date");
        }
    }

    public String getName(){
        return this.name;
    }

    public Category getCategory(){
        return this.category;
    }

    public Integer getRating(){
        return this.rating;
    }

    public LocalDate getCreatedDate(){
        return this.createdDate;
    }

    public LocalDate getModifiedDate(){
        return this.modifiedDate;
    }
}
