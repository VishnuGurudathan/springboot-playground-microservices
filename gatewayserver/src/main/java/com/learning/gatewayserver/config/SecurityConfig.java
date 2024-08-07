package com.learning.gatewayserver.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.ReactiveJwtDecoder;
import org.springframework.security.oauth2.jwt.ReactiveJwtDecoders;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.ReactiveJwtAuthenticationConverterAdapter;
import org.springframework.security.web.server.SecurityWebFilterChain;
import reactor.core.publisher.Mono;

/**
 * Security configuration class for setting up WebFlux security with OAuth2 resource server.
 */
@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

    private static final Logger log = LoggerFactory.getLogger(SecurityConfig.class);

    /**
     * Configures the security web filter chain.
     *
     * @param serverHttpSecurity the ServerHttpSecurity instance
     * @return the configured SecurityWebFilterChain
     */
    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity serverHttpSecurity) {
        serverHttpSecurity.authorizeExchange(exchangeSpec -> exchangeSpec
                        .pathMatchers("/public/**").permitAll()
                        //.anyExchange().authenticated()
                        .pathMatchers("/ecom/products/**").hasRole("PRODUCT_VIEWER"))
                .oauth2ResourceServer(oAuth2ResourceServerSpec -> oAuth2ResourceServerSpec
                        .jwt(jwtSpec -> jwtSpec.jwtAuthenticationConverter(grantedAuthoritiesExtractor())));
        serverHttpSecurity.csrf(ServerHttpSecurity.CsrfSpec::disable);

        return serverHttpSecurity.build();
    }

    /**
     * Provides a converter to extract granted authorities from JWT.
     *
     * @return the converter for extracting granted authorities
     */
    private Converter<Jwt, Mono<AbstractAuthenticationToken>> grantedAuthoritiesExtractor() {
        JwtAuthenticationConverter jwtAuthenticationConverter =
                new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter
                (new KeycloakGrantedAuthoritiesExtractor());
        return new ReactiveJwtAuthenticationConverterAdapter(jwtAuthenticationConverter);
    }

    /**
     * Configures the ReactiveJwtDecoder with the issuer URI from properties.
     *
     * @param properties the OAuth2ResourceServerProperties instance
     * @return the configured ReactiveJwtDecoder
     */
    @Bean
    public ReactiveJwtDecoder jwtDecoder(OAuth2ResourceServerProperties properties) {
        log.info("Configuring JWT decoder with issuer URI: {}", properties.getJwt().getIssuerUri());
        return token -> {
            log.debug("Attempting to decode JWT: {}", token);
            return ReactiveJwtDecoders.fromIssuerLocation(properties.getJwt().getIssuerUri()).decode(token)
                    .doOnNext(jwt -> log.debug("Successfully decoded JWT: {}", jwt.getIssuer()))
                    .doOnError(error -> log.error("Failed to decode JWT", error));
        };
    }

}