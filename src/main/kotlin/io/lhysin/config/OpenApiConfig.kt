package io.lhysin.config

import io.swagger.v3.oas.models.Components
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import io.swagger.v3.oas.models.security.SecurityRequirement
import io.swagger.v3.oas.models.security.SecurityScheme
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpHeaders


@Configuration
class OpenApiConfig {

    @Bean
    fun customOpenAPI(): OpenAPI {
        return OpenAPI()
            .info(
                Info()
                    .title("maria-api")
                    .description("maria-api server")
                    .version("v1")
            )
            .components(
                Components()
                    .addSecuritySchemes(
                        HttpHeaders.AUTHORIZATION,
                        SecurityScheme()
                            .type(SecurityScheme.Type.APIKEY)
                            .`in`(SecurityScheme.In.HEADER)
                            .name(HttpHeaders.AUTHORIZATION)
                            .description("Access Token")
                    )
            )
            .addSecurityItem(
                SecurityRequirement().addList(HttpHeaders.AUTHORIZATION)
            )
    }

}