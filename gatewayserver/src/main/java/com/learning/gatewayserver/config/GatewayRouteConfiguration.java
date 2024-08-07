package com.learning.gatewayserver.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;

/**
 * Configuration class for defining application routes using Spring Cloud Gateway.
 */
@Configuration
public class GatewayRouteConfiguration {

    /**
     * Defines the route configuration for the application.
     *
     * @param routeLocatorBuilder the RouteLocatorBuilder used to build the routes
     * @return a RouteLocator containing the route definitions
     */
    @Bean
    public RouteLocator routeConfig(RouteLocatorBuilder routeLocatorBuilder) {
        return routeLocatorBuilder.routes()
                .route("products_route", r -> r
                        .path("/ecom/products/**")
                        //TODO: Need to check if there is alternative to f.rewritePath if so implement that too
                        .filters(f -> f.rewritePath("/ecom/products/(?<segment>.*)", "/${segment}")
                                .addRequestHeader("X-Response-Time", LocalDateTime.now().toString())
                                 )
                        .uri("lb://PRODUCTS"))
//                .route("products_swagger_ui", r -> r
//                        .path("/ecom/products/swagger-ui/**")
//                        .filters(f -> f
//                                .rewritePath("/ecom/products/swagger-ui/(?<segment>.*)", "/swagger-ui/${segment}"))
//                        .uri("lb://PRODUCTS"))
                .build();
    }
}