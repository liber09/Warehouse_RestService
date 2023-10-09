package com.example.warehouse_restservice;

import com.example.warehouse_restservice.resource.Warehouse;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/all-products")
public class WarehouseResource {
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String allProducts() {
        Warehouse warehouse = new Warehouse();
        return warehouse.getAllProducts().toString();
    }


}