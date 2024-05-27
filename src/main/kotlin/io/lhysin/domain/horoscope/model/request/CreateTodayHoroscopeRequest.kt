package io.lhysin.domain.horoscope.model.request

import org.jetbrains.annotations.NotNull
import java.time.LocalDate

data class CreateTodayHoroscopeRequest(
    @field:NotNull
    val today: LocalDate,
)