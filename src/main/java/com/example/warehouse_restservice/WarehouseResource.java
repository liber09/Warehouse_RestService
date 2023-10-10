package com.example.warehouse_restservice;

import com.example.warehouse_restservice.resource.Warehouse;
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




}