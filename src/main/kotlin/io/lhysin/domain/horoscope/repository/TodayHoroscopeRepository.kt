package io.lhysin.domain.horoscope.repository

import io.lhysin.domain.horoscope.model.entity.TodayHoroscopeEntity
import io.lhysin.domain.horoscope.model.type.ZodiacSign
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.time.LocalDate

@Repository
interface TodayHoroscopeRepository : JpaRepository<TodayHoroscopeEntity, Long> {

    @Query("SELECT t FROM TodayHoroscopeEntity t WHERE t.today = :today AND t.zodiacSign = :zodiacSign ORDER BY RAND() LIMIT 1")
    fun findRandomByTodayAndZodiacSign(@Param("today") today: LocalDate, @Param("zodiacSign") zodiacSign: ZodiacSign): TodayHoroscopeEntity?

    fun findByMessageContaining(message: String, pageable: Pageable): Page<TodayHoroscopeEntity>

    @Query(value = "SELECT * FROM today_horoscope t WHERE t.message REGEXP '[一-龥]'", nativeQuery = true)
    fun findAllWithChineseCharacters(pageable: Pageable): Page<TodayHoroscopeEntity>
}