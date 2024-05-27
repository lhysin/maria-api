package io.lhysin.domain.horoscope.model.request

import java.time.LocalDate

data class TodayHoroscopeRequest(
    val birthDay: LocalDate,
)