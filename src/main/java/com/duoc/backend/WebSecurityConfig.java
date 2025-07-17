package com.duoc.backend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@Configuration
public class WebSecurityConfig {

        @Autowired
        JWTAuthorizationFilter jwtAuthorizationFilter;

        @Bean
        public SecurityFilterChain configure(HttpSecurity http) throws Exception {
                http
                                .csrf(csrf -> csrf.disable())
                                .authorizeHttpRequests(authz -> authz
                                                // Swagger
                                                .requestMatchers("/v3/api-docs/**", "/swagger-ui/**",
                                                                "/swagger-ui.html")
                                                .permitAll()
                                                // login
                                                .requestMatchers("/login", "/login/**").permitAll()

                                                // Pedido (v1 y v2)
                                                .requestMatchers("/pedido/**", "/pedido/v2/**").permitAll()

                                                // Combo (v1 y v2)
                                                .requestMatchers("/combo/**", "/combo/v2/**").permitAll()

                                                // Producto (v1 y v2)
                                                .requestMatchers("/producto/**", "/producto/v2/**").permitAll()

                                                // Operarios (v1 y v2)
                                                .requestMatchers("/operarios/**", "/operarios/v2/**").permitAll()

                                                // login
                                                .requestMatchers(HttpMethod.POST, "/login").permitAll()
                                                .requestMatchers(HttpMethod.GET, "/login").permitAll()

                                                // Pedidos
                                                .requestMatchers(HttpMethod.GET, "/pedido").permitAll()
                                                .requestMatchers(HttpMethod.GET, "/pedido/**").permitAll()
                                                .requestMatchers(HttpMethod.POST, "/pedido").permitAll()
                                                .requestMatchers(HttpMethod.POST, "/pedido/**").permitAll()
                                                .requestMatchers(HttpMethod.PUT, "/pedido/**").permitAll()
                                                .requestMatchers(HttpMethod.DELETE, "/pedido/**").permitAll()

                                                // Combo
                                                .requestMatchers(HttpMethod.GET, "/combo/**").permitAll()
                                                .requestMatchers(HttpMethod.POST, "/combo/**").permitAll()
                                                .requestMatchers(HttpMethod.PUT, "/combo/**").permitAll()
                                                .requestMatchers(HttpMethod.DELETE, "/combo/**").permitAll()

                                                // Productos
                                                .requestMatchers(HttpMethod.GET, "/producto/**").permitAll()
                                                .requestMatchers(HttpMethod.POST, "/producto/**").permitAll()
                                                .requestMatchers(HttpMethod.PUT, "/producto/**").permitAll()
                                                .requestMatchers(HttpMethod.DELETE, "/producto/**").permitAll()

                                                // Operarios
                                                .requestMatchers(HttpMethod.GET, "/operarios/**").permitAll()
                                                .requestMatchers(HttpMethod.POST, "/operarios/**").permitAll()
                                                .requestMatchers(HttpMethod.PUT, "/operarios/**").permitAll()
                                                .requestMatchers(HttpMethod.DELETE, "/operarios/**").permitAll()

                                                .anyRequest().authenticated());
                // .addFilterAfter(jwtAuthorizationFilter,
                // UsernamePasswordAuthenticationFilter.class);

                return http.build();
        }
}
