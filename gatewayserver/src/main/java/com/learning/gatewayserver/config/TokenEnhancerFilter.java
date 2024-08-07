package com.learning.gatewayserver.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.security.core.Authentication;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.stream.Collectors;

//@Component
@Deprecated
public class TokenEnhancerFilter implements GatewayFilter, Ordered {

    private static final Logger log = LoggerFactory.getLogger(TokenEnhancerFilter.class);

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        return ReactiveSecurityContextHolder.getContext()
                .map(ctx -> ctx.getAuthentication())
                .flatMap(auth -> enhanceRequest(exchange, auth, chain));
    }

    private Mono<Void> enhanceRequest(ServerWebExchange exchange, Authentication auth, GatewayFilterChain chain) {
        log.info("---------------------");
        ServerHttpRequest request = exchange.getRequest().mutate()
                .header("X-User-ID", auth.getName())
                .header("X-User-Roles", getRoles(auth))
                .build();
//        return chain.filter(exchange.mutate().request(request).build());

        logHeaders(request); // Log headers to confirm they are added
        ServerWebExchange exchange1 = exchange.mutate().request(request).build();
        return chain.filter(exchange1);
    }

    private String getRoles(Authentication auth) {
        return auth.getAuthorities().stream()
                .map(a -> a.getAuthority().replace("ROLE_", ""))// need to check if we need to replace or not.
                .collect(Collectors.joining(","));
    }

    private void logHeaders(ServerHttpRequest request) {
        request.getHeaders().forEach((key, value) -> log.info("Header '{}' = {}", key, value));
    }
    @Override
    public int getOrder() {
        return Ordered.LOWEST_PRECEDENCE;
    }
}
