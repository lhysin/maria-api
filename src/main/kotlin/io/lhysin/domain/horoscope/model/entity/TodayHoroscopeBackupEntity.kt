package io.lhysin.domain.horoscope.model.entity

import io.lhysin.domain.gpt.model.entity.GptModelProviderEntity
import io.lhysin.domain.horoscope.model.type.ZodiacSign
import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import java.time.LocalDate
import java.time.LocalDateTime

@Entity
@Table(name = "today_horoscope_backup")
class TodayHoroscopeBackupEntity (

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    var id: Long? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "gpt_model_provider_id", nullable = false)
    var gptModelProvider: GptModelProviderEntity,

    @Column(nullable = false)
    var today: LocalDate,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    var zodiacSign: ZodiacSign,

    @Column(nullable = false, columnDefinition = "TEXT")
    @Lob
    var message: String,

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    var createdAt: LocalDateTime? = null,
)