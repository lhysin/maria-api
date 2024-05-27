package io.lhysin.domain.horoscope.model.entity

import io.lhysin.domain.horoscope.model.type.ZodiacSign
import jakarta.persistence.*
import org.hibernate.annotations.Comment
import org.hibernate.annotations.CreationTimestamp
import java.time.LocalDate
import java.time.LocalDateTime

@Entity(name = "오늘의 운세")
@Table(name = "TODAY_HOROSOCPE")
class TodayHoroscopeEntity (

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    var id: Long? = null,

    @Column(nullable = false)
    @Comment("오늘")
    val today: LocalDate,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Comment("별자리")
    val zodiacSign: ZodiacSign,

    @Column(nullable = false)
    @Comment("gpt-model")
    val model: String,

    @Column(nullable = false)
    @Comment("gpt-provider")
    val provider: String,

    @Column(nullable = false, columnDefinition = "TEXT")
    @Comment("gpt-message")
    @Lob
    val message: String,

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    var createdAt: LocalDateTime? = null,
)