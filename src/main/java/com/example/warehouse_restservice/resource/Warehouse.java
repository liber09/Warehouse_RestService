package com.example.warehouse_restservice.resource;

import com.example.warehouse_restservice.resource.entities.Category;
import com.example.warehouse_restservice.resource.entities.Product;
import com.example.warehouse_restservice.resource.entities.ProductRecord;

import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

public class Warehouse {
    private final CopyOnWriteArrayList<Product> products = new CopyOnWriteArrayList<>();

    public boolean addProduct(String name, Category category, int rating, LocalDate creationDate, Boolean isTest, int testId) {
        if(name.trim().isEmpty()){
            System.out.println("Can't add products without name");
            return false;
        }

        if (category == null){
            category = Category.OTHER;
        }

        Product newProduct = new Product(name,category,rating, creationDate, isTest, testId);

        products.add(newProduct);

        return true;
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

    public List<ProductRecord> getAllProductsCreatedSince(LocalDate createdDate){
        return products.stream().filter(p -> p.getCreatedDate().isAfter(createdDate)).map(this::createRecordFromProduct).toList();
    }

    public List<ProductRecord> getAllModifiedProducts(){
        return products.stream().filter(p -> p.getModifiedDate() != p.getCreatedDate()).map(this::createRecordFromProduct).toList();
    }

    public List<Category> getAllCategoriesWithOneOrMoreProducts(){
        List<Category> categoriesWithProducts = new ArrayList<>();
        List<ProductRecord> tempProducts;
        for (Category category : EnumSet.allOf(Category.class)) {
            tempProducts = getAllProductsInCategory(category);
            if (!tempProducts.isEmpty()) {
                categoriesWithProducts.add(category);
            }
        }
        return categoriesWithProducts;
    }

    public long getNumberOfProductsInCategory(Category category){
        return products.stream().filter(p ->p.getCategory().equals(category)).count();
    }

    public List<ProductRecord> getProductsWithMaxRatingSortedByDate(){
        int maxRating = 10;
        return products.stream()
                .filter(p -> p.getRating() == maxRating &&
                        p.getCreatedDate().getMonth().equals(LocalDate.now().getMonth()))
                .map(this::createRecordFromProduct)
                .sorted(Comparator.comparing(ProductRecord::creationDate))
                .toList();
    }

    public boolean modifyProduct(UUID id, String name, Category category, int rating) throws Exception {
        Optional<Product> productWrapper = getProductById(id);
        if (productWrapper.isEmpty()) {
            throw(new Exception("No product to modify"));
        } else {
            LocalDate modifiedDate = LocalDate.now();
            Product product = productWrapper.get();
            if (!name.isEmpty()){
                product.setName(name);
                product.setModifiedDate(modifiedDate);
            } else {
                throw(new IllegalArgumentException("Product must have a name"));
            }

            if (!(category == null)){
                product.setCategory(category);
                product.setModifiedDate(modifiedDate);
            } else {
                throw(new IllegalArgumentException("Product must belong to a category"));
            }

            if (rating >= 0 && rating <= 10){
                product.setRating(rating);
                product.setModifiedDate(modifiedDate);
            } else{
                throw(new IllegalArgumentException("Rating must be between 0 and 10"));
            }
        }
        return true;
    }

    public Map<String, Long> getProductLetterAndProductCount(){
        return products.stream()
                .collect(Collectors
                        .groupingBy(product -> product.getName().substring(0,1),
                                Collectors.counting()));
    }

    private ProductRecord createRecordFromProduct(Product product){
        return new ProductRecord(product.getId(), product.getName(),
                product.getCategory(), product.getRating(), product.getCreatedDate(),
                product.getModifiedDate());
    }

    private void setupTestProducts(){
        LocalDate dateLastMonth = LocalDate.of(LocalDate.now().getYear(), LocalDate.now().getMonth().minus(1), LocalDate.now().getDayOfMonth());

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
