package io.lhysin.config

import io.lhysin.common.component.ActiveUserHolder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.domain.AuditorAware
import org.springframework.data.jpa.repository.config.EnableJpaAuditing
import java.util.*

@Configuration
@EnableJpaAuditing
class JpaConfig {

    @Bean
    fun auditorAware(): AuditorAware<String> {
        return AuditorAware<String> {
            Optional.ofNullable(ActiveUserHolder.get().userId)
        }
    }
}