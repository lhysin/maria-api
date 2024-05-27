package io.lhysin.domain.horoscope.repository

import io.lhysin.domain.horoscope.model.entity.TodayHoroscopeEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface TodayHoroscopeRepository : JpaRepository<TodayHoroscopeEntity, Long> {
}