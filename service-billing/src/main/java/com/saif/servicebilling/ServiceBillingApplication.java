package com.saif.servicebilling;

import com.saif.servicebilling.dao.BillRepository;
import com.saif.servicebilling.dao.ProductItemRepository;
import com.saif.servicebilling.entities.Bill;
import com.saif.servicebilling.entities.ProductItem;
import com.saif.servicebilling.feignClients.CustomerService;
import com.saif.servicebilling.feignClients.InventoryService;
import com.saif.servicebilling.nonentities.Customer;
import com.saif.servicebilling.nonentities.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.Collection;
import java.util.stream.Stream;

@EnableDiscoveryClient
@EnableFeignClients
@SpringBootApplication
public class ServiceBillingApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServiceBillingApplication.class, args);
    }

    @Bean
    CommandLineRunner run(BillRepository billRepository,
                          ProductItemRepository productItemRepository,
                          CustomerService customerService,
                          InventoryService inventoryService){
        return args -> {
            Customer customer = customerService.findCustomerById(1L);
            System.out.println("*********************");
            System.out.println(customer);
            System.out.println("*********************");
            System.out.println("*********************");
            Collection<Product> products = inventoryService.findAllProducts().getContent();
            products.forEach(System.out::println);
            System.out.println("*********************");
            System.out.println("*********************");
            Bill bill = billRepository.save(new Bill(null,LocalDate.now(), 1L, null, null));
            billRepository.findAll().forEach(System.out::println);

            Stream.of(1, 2, 3).forEach(product -> {
                productItemRepository.save(
                        new ProductItem( null, 1000+Math.random()*9000,
                                (int) (100+Math.random()*900), product.longValue(), null,
                                bill
                        ));
            });
            productItemRepository.findAll().forEach(System.out::println);
        };
    }
}
@RestController
class BillingRestController{
    @Autowired
    private BillRepository billRepository;
    @Autowired
    private ProductItemRepository productItemRepository;
    @Autowired
    private CustomerService customerService;
    @Autowired
    private InventoryService inventoryService;

    @GetMapping("fullBill/{id}")
    public Bill getBill(@PathVariable(name = "id") Long id){
        Bill foundBill = billRepository.findById(id).orElse(null);
        assert foundBill != null;
        Customer customer = customerService.findCustomerById(foundBill.getCustomerID());
        foundBill.setCustomer(customer);
        foundBill.getProductItems().forEach(productItem -> {
            Product product = inventoryService.findProductById(productItem.getProductID());
            productItem.setProduct(product);
        });
        return foundBill;
    }
}
