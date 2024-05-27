package io.lhysin.domain.horoscope.model.response

import java.time.LocalDate

data class TodayHoroscopeResponse(
    val birthDay: LocalDate,
    val zodiacSignKoreanName: String,
    val message: String,
)