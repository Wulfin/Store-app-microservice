package com.saif.serviceinventory;

import com.saif.serviceinventory.dao.ProductRepository;
import com.saif.serviceinventory.entities.Product;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;

import java.util.stream.Stream;

@EnableDiscoveryClient
@SpringBootApplication
public class ServiceInventoryApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServiceInventoryApplication.class, args);
    }

    @Bean
    CommandLineRunner run(ProductRepository productRepository){
        return args -> {
            Stream.of("product 1", "product 2", "product 3").forEach(product -> {
                productRepository.save(new Product(null, 1000+Math.random()*9000, product));
            });
            productRepository.findAll().forEach(System.out::println);
        };
    }
}
