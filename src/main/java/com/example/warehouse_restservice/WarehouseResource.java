package com.example.warehouse_restservice;
import com.example.warehouse_restservice.resource.Warehouse;
import com.example.warehouse_restservice.resource.entities.Category;
import com.example.warehouse_restservice.resource.entities.Product;
import com.example.warehouse_restservice.resource.entities.ProductRecord;
import com.example.warehouse_restservice.resource.entities.helpers.JacksonObjectMapper;
import com.example.warehouse_restservice.resource.interceptors.Log;
import jakarta.inject.Inject;
import jakarta.interceptor.Interceptors;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

@Path("/products")
@Interceptors(Log.class)
public class WarehouseResource {
    private final static Logger logger = LoggerFactory.getLogger(ProductRecord.class);
    private final JacksonObjectMapper objectMapper;
    private final Warehouse warehouse;
    @Inject
    public WarehouseResource(){
        objectMapper = new JacksonObjectMapper();
        this.warehouse = new Warehouse();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response allProducts() {
        List<ProductRecord> products = warehouse.getAllProducts();
        if (products.isEmpty()) {
            throw new NotFoundException("No products were found");
        }

        // Create a mutable copy of the list
        List<ProductRecord> mutableProducts = new ArrayList<>(products);

        // Sort the mutable copy
        mutableProducts.sort(Comparator.comparing(ProductRecord::id));

        // Now you can use the sorted list or perform other operations

        return Response.status(Response.Status.OK)
                .entity(mutableProducts)  // Return the sorted list in the response
                .type(MediaType.APPLICATION_JSON)
                .build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{id}")
    public String productById(@PathParam("id") UUID id){
        return warehouse.getProductRecordById(id).toString();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/category/{category}")
    public String getAllProductsInCategory(@PathParam("category") String wantedCategory){
        Category category = Category.valueOf(wantedCategory.toUpperCase());
        return warehouse.getAllProductsInCategory(category).toString();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public void addProduct(@Valid Product product){
        warehouse.addProduct(product);

    }


}