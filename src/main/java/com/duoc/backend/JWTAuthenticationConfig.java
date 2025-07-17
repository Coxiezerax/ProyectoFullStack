package com.duoc.backend;

import io.jsonwebtoken.Jwts;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import static com.duoc.backend.Constants.SUPER_SECRET_KEY;
import static com.duoc.backend.Constants.getSigningKey;

@Configuration
public class JWTAuthenticationConfig {

        public String getJWTToken(UserDetails userDetails) {
                Map<String, Object> claims = new HashMap<>();

                claims.put("authorities", userDetails.getAuthorities()
                                .stream()
                                .map(GrantedAuthority::getAuthority)
                                .collect(Collectors.toList()));

                String token = Jwts.builder()
                                .claims()
                                .add(claims)
                                .subject(userDetails.getUsername())
                                .issuedAt(new Date(System.currentTimeMillis()))
                                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 1440)) // 1 día
                                .and()
                                .signWith(getSigningKey(SUPER_SECRET_KEY))
                                .compact();

                return "Bearer " + token;
        }
}
