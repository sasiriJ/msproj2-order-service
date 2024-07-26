package com.sasiri.productapp.orderservice.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;

@Configuration
public class OpenAPIConfig {
    @Bean
    public OpenAPI orderServiceAPI(){
        return new OpenAPI().info(new Info().title("Order Service API")
                        .description("RestAPI for order service")
                        .version("v0.0.1")
                        .license(new License().name("MIT")))
                .externalDocs(new ExternalDocumentation()
                        .description("External Documentation links")
                        .url("https://order-service-dummy.com/docs")
                );
    }
}
