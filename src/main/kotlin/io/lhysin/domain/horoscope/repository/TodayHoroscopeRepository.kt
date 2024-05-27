package io.lhysin.domain.horoscope.repository

import io.lhysin.domain.horoscope.model.entity.TodayHoroscopeEntity
import io.lhysin.domain.horoscope.model.type.ZodiacSign
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.time.LocalDate

@Repository
interface TodayHoroscopeRepository : JpaRepository<TodayHoroscopeEntity, Long> {

    @Query("SELECT t FROM today_horosocpe t WHERE t.today = :today AND t.zodiacSign = :zodiacSign ORDER BY RANDOM() LIMIT 1")
    fun findRandomByTodayAndZodiacSign(@Param("today") today: LocalDate, @Param("zodiacSign") zodiacSign: ZodiacSign): TodayHoroscopeEntity?
}