package io.lhysin.domain.horoscope.model.response

import io.lhysin.domain.horoscope.model.type.ZodiacSign

data class TodayHoroscopeDto(
    val model: String,
    val provider: String,
    val zodiacSign: ZodiacSign,
    val message: String,
)