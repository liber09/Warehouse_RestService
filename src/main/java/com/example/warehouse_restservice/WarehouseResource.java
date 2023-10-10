package com.example.warehouse_restservice;

import com.example.warehouse_restservice.resource.Warehouse;
import com.example.warehouse_restservice.resource.entities.Category;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import java.util.UUID;


@Path("/products")
public class WarehouseResource {
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String allProducts() {
        Warehouse warehouse = new Warehouse();
        return warehouse.getAllProducts().toString();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{id}")
    public String productById(@PathParam("id") UUID id){
        Warehouse warehouse = new Warehouse();
        return warehouse.getProductRecordById(id).toString();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/category/{category}")
    public String getAllProductsInCategory(@PathParam("category") String wantedCategory){
        Warehouse warehouse = new Warehouse();
        Category category = Category.valueOf(wantedCategory.toUpperCase());
        return warehouse.getAllProductsInCategory(category).toString();
    }

}