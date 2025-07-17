package com.duoc.backend.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.*;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAPIConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API Sistema de Pedidos Foodtruck")
                        .version("1.0")
                        .description("Documentación")
                        .contact(new Contact()
                                .name("VALFER")
                                .email("contacto@foodtruck.cl")))
                .addServersItem(new Server()
                        .url("http://localhost:8180")
                        .description("Servidor Local"));
    }
}
