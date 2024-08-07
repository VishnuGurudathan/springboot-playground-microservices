package com.learning.gatewayserver.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

@Deprecated // need to check as part of logging
public class RequestLoggingFilter implements WebFilter {
    private static final Logger logger = LoggerFactory.getLogger(RequestLoggingFilter.class);

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        logger.debug("Request headers: {}", exchange.getRequest().getHeaders());
        return chain.filter(exchange);
    }
}