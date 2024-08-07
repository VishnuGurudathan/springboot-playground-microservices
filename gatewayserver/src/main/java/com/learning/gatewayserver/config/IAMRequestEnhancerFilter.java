package com.learning.gatewayserver.config;

import com.learning.gatewayserver.constants.GatewayConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * A filter that enhances HTTP requests with user information.
 * This filter adds custom headers to the request based on the authenticated user's details.
 */
@Component
public class IAMRequestEnhancerFilter implements GlobalFilter, Ordered {

    private static final Logger log = LoggerFactory.getLogger(IAMRequestEnhancerFilter.class);

    /**
     * Filters the incoming request and enhances it with user information if the user is authenticated.
     *
     * @param exchange the current server exchange
     * @param chain the gateway filter chain
     * @return a Mono that indicates when request processing is complete
     */
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        return ReactiveSecurityContextHolder.getContext()
                .filter(c -> c.getAuthentication() != null)
                .flatMap(auth -> enhanceRequest(exchange, auth.getAuthentication(), chain))
                .switchIfEmpty(chain.filter(exchange));
    }

    /**
     * Enhances the request with user information headers.
     *
     * @param exchange the current server exchange
     * @param auth the authentication object containing user details
     * @param chain the gateway filter chain
     * @return a Mono that indicates when request processing is complete
     */
    private Mono<Void> enhanceRequest(ServerWebExchange exchange, Authentication auth, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest().mutate()
                .header("X-User-ID", auth.getName())
                .header("X-User-Roles", getRoles(auth))
                .header("X-User-Email", extractEmail.apply(auth))
                .build();
        logHeaders.accept(request); // Log headers to confirm they are added

        return chain.filter(exchange.mutate().request(request).build());
    }

    /**
     * Extracts the email from the authentication object.
     */
    private final Function<Authentication, String> extractEmail = auth -> {
        Jwt jwt = (Jwt) auth.getPrincipal();
        return jwt.getClaim("email");
    };

    /**
     * Retrieves the roles from the authentication object and formats them as a comma-separated string.
     *
     * @param auth the authentication object containing user details
     * @return a comma-separated string of roles
     */
    private String getRoles(Authentication auth) {
        return auth.getAuthorities().stream()
                .map(a -> a.getAuthority().replace(GatewayConstants.ROLE, "")) // need to check if we need to replace or not.
                .collect(Collectors.joining(","));
    }

    /**
     * Logs the headers of the request for debugging purposes.
     */
    private final Consumer<ServerHttpRequest> logHeaders = request ->
            request.getHeaders().forEach((key, value) ->
                    log.debug("Header '{}' = {}", key, value));

    /**
     * Specifies the order of the filter.
     *
     * @return the order value
     */
    @Override
    public int getOrder() {
        return Ordered.LOWEST_PRECEDENCE;
    }
}