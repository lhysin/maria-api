package io.lhysin.config

import io.lhysin.common.handler.ActiveProfileHandler
import io.lhysin.common.interceptor.JwtTokenInterceptor
import io.lhysin.common.interceptor.RequestLoggingInterceptor
import io.lhysin.common.interceptor.UserRoleInterceptor
import io.lhysin.common.provider.JwtTokenProvider
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.Ordered
import org.springframework.web.servlet.HandlerInterceptor
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class WebConfig(
    private val jwtTokenProvider: JwtTokenProvider,
    private val activeProfileHandler: ActiveProfileHandler,
) : WebMvcConfigurer {

    override fun addCorsMappings(registry: CorsRegistry) {
        registry.addMapping("/**")
            .allowedOrigins("*")
            .allowedMethods("*")
            .allowedHeaders("*")
            .allowCredentials(false)
    }

    override fun addInterceptors(registry: InterceptorRegistry) {
        registry.addInterceptor(jwtTokenInterceptor()).order(Ordered.HIGHEST_PRECEDENCE)
        registry.addInterceptor(userRoleInterceptor()).order(Ordered.HIGHEST_PRECEDENCE + 1)
    }

    @Bean
    fun requestLoggingInterceptor(): HandlerInterceptor {
        return RequestLoggingInterceptor()
    }

    @Bean
    fun jwtTokenInterceptor(): HandlerInterceptor {
        return JwtTokenInterceptor(
            jwtTokenProvider,
            activeProfileHandler
        )
    }

    @Bean
    fun userRoleInterceptor(): UserRoleInterceptor {
        return UserRoleInterceptor()
    }

}