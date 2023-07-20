package com.saif.servicebilling.feignClients;

import com.saif.servicebilling.nonentities.Product;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.hateoas.PagedModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "INVENTORY-SERVICE")
public interface InventoryService {
    @GetMapping("/products/{id}?projection=full")
    public Product findProductById(@PathVariable(name = "id") Long id);
    @GetMapping("/products?projection=full")
    public PagedModel<Product> findAllProducts();
}
