package com.saif.servicecustomer;

import com.saif.servicecustomer.dao.CustomerRepository;
import com.saif.servicecustomer.entity.Customer;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;

import java.util.stream.Stream;

@EnableDiscoveryClient
@SpringBootApplication
public class ServiceCustomerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServiceCustomerApplication.class, args);
    }

    @Bean
    CommandLineRunner run(CustomerRepository customerRepository){
        return args -> {
            Stream.of("customer 1", "customer 2", "customer 3").forEach(customer -> {
                customerRepository.save(new Customer(null, customer, customer.concat("@gmail.com")));
            });
            customerRepository.findAll().forEach(System.out::println);
        };
    }
}
