package com.saif.servicebilling.feignClients;

import com.saif.servicebilling.nonentities.Customer;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "CUSTOMER-SERVICE")
public interface CustomerService {
    @GetMapping("/customers/{id}?projection=full")
    public Customer findCustomerById(@PathVariable(name = "id") Long id);
}
