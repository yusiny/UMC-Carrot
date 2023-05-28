package com.umc.carrotmarket.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(
        info = @Info(
                title = "당근마켓 API 명세서",
                description = "UMC Spring Boot 당근마켓 클론코딩",
                version = "v1",
                contact = @Contact(
                        name = "김유신",
                        email = "sample@email.co.kr"
                )
        )
)

@Configuration
public class SwaggerConfig {
}
