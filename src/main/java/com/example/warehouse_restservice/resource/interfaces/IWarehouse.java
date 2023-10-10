package com.example.warehouse_restservice.resource.interfaces;

import com.example.warehouse_restservice.resource.entities.Category;
import com.example.warehouse_restservice.resource.entities.Product;
import com.example.warehouse_restservice.resource.entities.ProductRecord;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public interface IWarehouse {
    boolean addProduct(String name, Category category, int rating, LocalDate creationDate, Boolean isTest, int testId);

    List<ProductRecord> getAllProducts();

    Optional<ProductRecord> getProductRecordById(UUID id);

    Optional<Product> getProductById(UUID id);

    List<ProductRecord> getAllProductsInCategory(Category category);

    List<ProductRecord> getAllProductsCreatedSince(LocalDate createdDate);

    List<ProductRecord> getAllModifiedProducts();

    List<Category> getAllCategoriesWithOneOrMoreProducts();

    long getNumberOfProductsInCategory(Category category);

    List<ProductRecord> getProductsWithMaxRatingSortedByDate();

    boolean modifyProduct(UUID id, String name, Category category, int rating) throws Exception;

    Map<String, Long> getProductLetterAndProductCount();
}
