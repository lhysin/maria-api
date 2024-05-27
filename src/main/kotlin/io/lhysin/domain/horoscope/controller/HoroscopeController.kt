package io.lhysin.domain.horoscope.controller

import io.lhysin.common.annotaion.AdminRole
import io.lhysin.domain.horoscope.model.request.TodayHoroscopeRequest
import io.lhysin.domain.horoscope.model.response.TodayHoroscopeResponse
import io.lhysin.domain.horoscope.service.HoroscopeService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

@Tag(name = "Horoscope", description = "Horoscope Controller")
@RestController
@RequestMapping("/api/v1/horoscope")
@Validated
class HoroscopeController (
    private val horoscopeService : HoroscopeService,
){

    @Operation(summary = "Today horoscope", description = "find Today horoscope.")
    @GetMapping
    fun todayHoroscope(@Valid todayHoroscopeRequest: TodayHoroscopeRequest) : TodayHoroscopeResponse {
        return horoscopeService.todayHoroscope(todayHoroscopeRequest)
    }

    @AdminRole
    @Operation(summary = "Create Today horoscope", description = "Create Today horoscope.")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun createTodayHoroscope() {
        horoscopeService.createBulkTodayHoroscope()
    }
}