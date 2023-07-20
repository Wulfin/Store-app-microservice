package com.saif.servicecompany;

import com.saif.servicecompany.dao.CompanyRepository;
import com.saif.servicecompany.entities.Company;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;

import java.util.stream.Stream;
@EnableDiscoveryClient
@SpringBootApplication
public class ServiceCompanyApplication{

    public static void main(String[] args) {
        SpringApplication.run(ServiceCompanyApplication.class, args);
    }

    @Bean
    CommandLineRunner run(CompanyRepository companyRepository){
        return args -> {
            Stream.of("A","B","C").forEach(company ->{
                companyRepository.save(new Company(null,company,100+Math.random()*900));
            });
            companyRepository.findAll().forEach(System.out::println);
        };
    }
}
