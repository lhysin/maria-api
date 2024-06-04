package io.lhysin.domain.horoscope.repository

import io.lhysin.domain.horoscope.model.entity.TodayHoroscopeBackupEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface TodayHoroscopeBackupRepository : JpaRepository<TodayHoroscopeBackupEntity, Long> {
}