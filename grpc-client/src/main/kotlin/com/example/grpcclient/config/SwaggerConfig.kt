package com.example.grpcclient.config

import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class SwaggerConfig {

    @Bean
    fun openAPI(): OpenAPI {
        return OpenAPI()
            .info(Info()
                .title("gRPC Client API")
                .description("These are APIs for gRPC attachment.")
                .version("1.0.0"))
    }

}