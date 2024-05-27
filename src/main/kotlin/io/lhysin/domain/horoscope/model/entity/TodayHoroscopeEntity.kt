package io.lhysin.domain.horoscope.model.entity

import io.lhysin.domain.horoscope.model.type.ZodiacSign
import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import java.time.LocalDate
import java.time.LocalDateTime

@Entity
@Table(name = "today_horoscope")
class TodayHoroscopeEntity (

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    var id: Long? = null,

    @Column(nullable = false)
    var today: LocalDate,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    var zodiacSign: ZodiacSign,

    @Column(nullable = false)
    var model: String,

    @Column(nullable = false)
    var provider: String,

    @Column(nullable = false, columnDefinition = "TEXT")
    @Lob
    var message: String,

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    var createdAt: LocalDateTime? = null,
)