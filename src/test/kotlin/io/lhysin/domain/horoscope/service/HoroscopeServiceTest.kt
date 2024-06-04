package io.lhysin.domain.horoscope.service

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles

@SpringBootTest
@ActiveProfiles("local")
class HoroscopeServiceTest(
    @Autowired
    private var horoscopeBackupService: HoroscopeBackupService
) {

    @Test
    fun backup_horoscope () {
        horoscopeBackupService.backupAndDeleteByMessage("gptxyy")
    }

}