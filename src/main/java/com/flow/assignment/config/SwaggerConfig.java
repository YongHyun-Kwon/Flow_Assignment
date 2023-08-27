package com.flow.assignment.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    // Swagger 연동
    @Bean
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
                .group("v1-extension")
                .pathsToMatch("/extension/v1/**")
                .build();
    }

    // Swagger API 명세서 UI
    @Bean
    public OpenAPI springStoryOpenApi() {
        return new OpenAPI()
                .info(new Info().title("Extension API")
                .description("Extension API Docs")
                .version("v.0.0.1"));
    }
}
