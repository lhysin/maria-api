package io.lhysin.domain.horoscope.model.type

import java.time.LocalDate

enum class ZodiacSign(val startMonth: Int, val startDay: Int, val endMonth: Int, val endDay: Int, val koreanName: String) {
    CAPRICORN(12, 22, 1, 19, "염소자리"),
    AQUARIUS(1, 20, 2, 18, "물병자리"),
    PISCES(2, 19, 3, 20, "물고기자리"),
    ARIES(3, 21, 4, 19, "양자리"),
    TAURUS(4, 20, 5, 20, "황소자리"),
    GEMINI(5, 21, 6, 20, "쌍둥이자리"),
    CANCER(6, 21, 7, 22, "게자리"),
    LEO(7, 23, 8, 22, "사자자리"),
    VIRGO(8, 23, 9, 22, "처녀자리"),
    LIBRA(9, 23, 10, 22, "천칭자리"),
    SCORPIO(10, 23, 11, 21, "전갈자리"),
    SAGITTARIUS(11, 22, 12, 21, "사수자리");

    companion object {
        fun getZodiacSign(date: LocalDate): ZodiacSign {
            val month = date.monthValue
            val day = date.dayOfMonth

            return values().first {
                (month == it.startMonth && day >= it.startDay) ||
                    (month == it.endMonth && day <= it.endDay) ||
                    (month > it.startMonth && month < it.endMonth) ||
                    (it.startMonth > it.endMonth && (month > it.startMonth || month < it.endMonth))
            }
        }
    }
}