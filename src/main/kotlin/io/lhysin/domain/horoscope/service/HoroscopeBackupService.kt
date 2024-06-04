package io.lhysin.domain.horoscope.service

import io.lhysin.common.provider.PagedProvider
import io.lhysin.domain.horoscope.model.entity.TodayHoroscopeBackupEntity
import io.lhysin.domain.horoscope.model.entity.TodayHoroscopeEntity
import io.lhysin.domain.horoscope.repository.TodayHoroscopeBackupRepository
import io.lhysin.domain.horoscope.repository.TodayHoroscopeRepository
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class HoroscopeBackupService(
    private val todayHoroscopeRepository: TodayHoroscopeRepository,
    private val todayHoroscopeBackupRepository: TodayHoroscopeBackupRepository,
) {


    private val todayHoroscopePagedProvider: PagedProvider<TodayHoroscopeEntity> = PagedProvider(
        chunkSize = 1000
    )

    fun backupAndDeleteByMessage(message: String) {
        backupAndDelete(
            reader = { offset, limit ->
                todayHoroscopeRepository.findByMessageContaining(
                    message,
                    PageRequest.of(offset / limit, limit)
                ).toList()
            }
        )

        backupAndDelete(
            reader = { offset, limit ->
                todayHoroscopeRepository.findAllWithChineseCharacters(
                    PageRequest.of(offset / limit, limit)
                ).toList()
            }
        )
    }

    private fun backupAndDelete(reader: (Int, Int) -> List<TodayHoroscopeEntity>) {
        todayHoroscopePagedProvider.paged(reader) { list ->
            val backupList = list.map { it.toBackupEntity() }
            todayHoroscopeBackupRepository.saveAll(backupList)
            todayHoroscopeRepository.deleteAll(list)
        }
    }

    private fun TodayHoroscopeEntity.toBackupEntity() = TodayHoroscopeBackupEntity(
        gptModelProvider = this.gptModelProvider,
        today = this.today,
        zodiacSign = this.zodiacSign,
        message = this.message
    )
}