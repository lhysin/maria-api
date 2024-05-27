package io.lhysin.config

import com.github.javafaker.Faker
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.util.*

@Configuration
class BeanConfig {

    @Bean
    fun faker() : Faker {
        return Faker(Locale("ko"));
    }
}