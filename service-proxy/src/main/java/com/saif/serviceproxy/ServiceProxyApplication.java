package com.saif.serviceproxy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.discovery.ReactiveDiscoveryClient;
import org.springframework.cloud.gateway.discovery.DiscoveryClientRouteDefinitionLocator;
import org.springframework.cloud.gateway.discovery.DiscoveryLocatorProperties;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.context.annotation.Bean;

@EnableDiscoveryClient
@EnableHystrix
@SpringBootApplication
public class ServiceProxyApplication {

    /*@Bean
    RouteLocator gatewayRoutes(RouteLocatorBuilder builder){
        return builder.routes()
                .route(r -> r.path("/company/**").uri("lb://COMPANY-SERVICE"))
                .build();
    }*/

    // adding Routes statically
    @Bean
    RouteLocator gatewayRoutes (RouteLocatorBuilder builder){
        return builder.routes()
                .route(r->r.path("/countries/**")
                        .filters(f->f
                                .rewritePath("/countries/(?<segment>.*)","/v3.1/${segment}")
                        )
                        .uri("https://restcountries.com")
                )
                .route(r->r.path("/muslimsalat/**")
                        .filters(f->f
                                .rewritePath("/muslimsalat/(?<segment>.*)","/v1/${segment}")
                        )
                        .uri("https://api.aladhan.com")
                )
                .build();
    }

    @Bean
    DiscoveryClientRouteDefinitionLocator gatewayDynamicRoutes(
            ReactiveDiscoveryClient rdc,
            DiscoveryLocatorProperties dlp) {
        return new DiscoveryClientRouteDefinitionLocator(rdc, dlp);
    }
    public static void main(String[] args) {
        SpringApplication.run(ServiceProxyApplication.class, args);
    }

}
