package com.example.warehouse_restservice.resource;

import com.example.warehouse_restservice.resource.entities.Category;
import com.example.warehouse_restservice.resource.entities.Product;
import com.example.warehouse_restservice.resource.entities.ProductRecord;
import jakarta.enterprise.inject.Default;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

@Default
public class Warehouse {
    private final CopyOnWriteArrayList<Product> products = new CopyOnWriteArrayList<Product>();
    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    Validator validator = factory.getValidator();


    public void addProduct(String name, Category category, int rating, String creationDate, Boolean isTest, int testId) {
        if(name.trim().isEmpty()){
            throw new IllegalArgumentException("Name can not be empty");
        }

        if (category == null){
            category = Category.OTHER;
        }

        Product newProduct = new Product(name,category,rating, creationDate, isTest, testId);
        Set<ConstraintViolation<Product>> violations = validator.validate(newProduct);
        ArrayList<String> errors = new ArrayList<>();
        for (ConstraintViolation<Product> violation : violations) {
            errors.add(violation.getMessage());
        }
        products.add(newProduct);

    }

    public void addProduct(Product product){
        products.add(product);
    }


    public List<ProductRecord> getAllProducts(){
        setupTestProducts();
        return products.stream().map(this::createRecordFromProduct).toList();
    }


    public Optional<ProductRecord> getProductRecordById(UUID id) {
        setupTestProducts();
        return products.stream()
                .filter(p -> p.getId().equals(id)).map(this::createRecordFromProduct).findFirst();
    }


    public Optional<Product> getProductById(UUID id) {
        return products.stream()
                .filter(p -> p.getId().equals(id)).findFirst();
    }


    public List<ProductRecord> getAllProductsInCategory(Category category){
        setupTestProducts();
        return products.stream().filter(p -> p.getCategory().equals(category)).map(this::createRecordFromProduct).sorted(Comparator.comparing(ProductRecord::name)).toList();
    }

    private ProductRecord createRecordFromProduct(Product product){
        return new ProductRecord(product.getId(), product.getName(),
                product.getCategory(), product.getRating(), product.getCreatedDate(),
                product.getModifiedDate());
    }

    private void setupTestProducts(){
        if (products.isEmpty()){
            String dateLastMonth = LocalDate.of(LocalDate.now().getYear(), LocalDate.now().getMonth().minus(1), LocalDate.now().getDayOfMonth()).toString();

            addProduct("Diesel tshirt",Category.TSHIRTS,5,dateLastMonth, true, 1);
            addProduct("Calvin Klein tshirt",Category.TSHIRTS,7,dateLastMonth, true, 2);
            addProduct("Alpha industries tshirt",Category.TSHIRTS,7,dateLastMonth, true, 3);
            addProduct("H&M jeans",Category.JEANS,5,dateLastMonth, true, 4);
            addProduct("Softpants",Category.PANTS,4,dateLastMonth, true, 5);
            addProduct("Rayban sunglasses",Category.ACCESSORIES,5,dateLastMonth, true, 6);
            addProduct("Adidas cap",Category.HATS,5,dateLastMonth, true, 7);
            addProduct("Nike cap",Category.HATS,6,dateLastMonth, true,8);
            addProduct("Hugo Boss tshirt",Category.TSHIRTS,10,dateLastMonth, true,9);
            addProduct("Calvin Klein skinny jeans",Category.JEANS,10,dateLastMonth, true,10);
            addProduct("Nike shoes", Category.SHOES, 10, dateLastMonth, true,11);
        }
    }
}
