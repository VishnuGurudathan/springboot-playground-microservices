package com.learning.gatewayserver.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * A converter that extracts granted authorities from a JWT token.
 * It extracts roles from both `realm_access` and `resource_access` claims.
 */
public class KeycloakGrantedAuthoritiesExtractor implements Converter<Jwt, Collection<GrantedAuthority>> {
    private static final Logger log = LoggerFactory.getLogger(KeycloakGrantedAuthoritiesExtractor.class);

    /**
     * Converts the given JWT token into a collection of granted authorities.
     *
     * @param jwt the JWT token
     * @return a collection of granted authorities
     */
    @Override
    public Collection<GrantedAuthority> convert(Jwt jwt) {
        log.debug("Converting JWT claims: {}", jwt.getClaims());

        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();

        // Extract roles from realm_access
        grantedAuthorities.addAll(extractRoles(jwt, "realm_access"));

        // Extract roles from resource_access
        grantedAuthorities.addAll(extractRoles(jwt, "resource_access"));

        return grantedAuthorities;
    }

    /**
     * Extracts roles from the specified claim in the JWT token.
     *
     * @param jwt the JWT token
     * @param claimName the name of the claim to extract roles from
     * @return a collection of granted authorities
     */
    private Collection<GrantedAuthority> extractRoles(Jwt jwt, String claimName) {
        Map<String, Object> access = (Map<String, Object>) jwt.getClaims().get(claimName);
        if (access != null && !access.isEmpty()) {
            List<String> roles = (List<String>) access.get("roles");
            if (roles != null) {
                return roles.stream()
                        .map(role -> new SimpleGrantedAuthority("ROLE_" + role.toUpperCase()))
                        .collect(Collectors.toList());
            }
        }
        return List.of(); // Return an immutable empty list if no roles are found
    }
}