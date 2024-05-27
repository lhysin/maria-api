package io.lhysin.common

import jakarta.annotation.PostConstruct
import org.springframework.stereotype.Component

@Component
class Initializer(
) {
    @PostConstruct
    fun createBasicData() {
    }
}