package io.lhysin.domain.horoscope.service

import io.lhysin.domain.horoscope.model.entity.TodayHoroscopeEntity
import io.lhysin.domain.horoscope.model.request.TodayHoroscopeRequest
import io.lhysin.domain.horoscope.model.response.TodayHoroscopeDto
import io.lhysin.domain.horoscope.model.response.TodayHoroscopeResponse
import io.lhysin.domain.horoscope.model.type.ZodiacSign
import io.lhysin.domain.horoscope.repository.TodayHoroscopeRepository
import io.lhysin.external.client.Gpt4FreeClient
import io.lhysin.external.model.request.Gpt4FreeMessage
import io.lhysin.external.model.request.Gpt4FreeRequest
import jakarta.validation.Valid
import mu.KotlinLogging
import org.springframework.scheduling.annotation.Async
import org.springframework.scheduling.annotation.EnableAsync
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.time.YearMonth

private val logger = KotlinLogging.logger {}

@EnableAsync
@Service
class HoroscopeService(
    private val gpt4FreeClient: Gpt4FreeClient,
    private val todayHoroscopeRepository: TodayHoroscopeRepository,
) {

    @Async
    fun createBulkTodayHoroscope() {
        val now = LocalDate.now()
        val lastDay = YearMonth.of(now.year, 12).atEndOfMonth()
        var day = 0L
        while (true) {
            val today = now.plusDays(day)
            for (zodiacSign in ZodiacSign.values()) {
                try {
                    val dto = createTodayHoroscope(zodiacSign, today)
                    if (dto.message.isNotBlank()) {
                        todayHoroscopeRepository.save(
                            TodayHoroscopeEntity(
                                model = dto.model,
                                provider = dto.provider,
                                today = today,
                                zodiacSign = dto.zodiacSign,
                                message = dto.message
                            )
                        )
                    }
                } catch (e: Exception) {
                    logger.error{"fail createTodayHoroscope(zodiacSign, today)"}
                }
                Thread.sleep(1000 * 7)
            }
            day++
            if (today.isAfter(lastDay)) {
                break
            }
        }
    }

    private fun getFutureDatesOfMonth(today: LocalDate): List<LocalDate> {
        val year = today.year
        val month = today.monthValue

        // 현재 월의 마지막 날짜
        val lastDayOfMonth = LocalDate.of(year, month, today.lengthOfMonth())

        // 현재일자부터 해당 월의 마지막 날까지의 리스트 생성
        val futureDatesOfMonth = mutableListOf<LocalDate>()
        var currentDate = today
        while (currentDate.isBefore(lastDayOfMonth) || currentDate.isEqual(lastDayOfMonth)) {
            futureDatesOfMonth.add(currentDate)
            currentDate = currentDate.plusDays(1)
        }

        return futureDatesOfMonth

    }

    private fun createTodayHoroscope(zodiacSign: ZodiacSign, now: LocalDate): TodayHoroscopeDto {

        val response = gpt4FreeClient.completions(
            Gpt4FreeRequest(
                model = "",
                provider = "",
                max_tokens = 500,
                temperature = 0.9,
                top_p = 0.9,
                messages = listOf(
                    Gpt4FreeMessage(
                        role = "system",
                        content = "- 너는 항상 상세하고 친절한 답변을 제공하는 운세 및 점성술 전문가야. - 아주 구체적이고 정확한 답변을 하는 운세전문가야 - 운세관련 지식이 풍부해 -점성술사의 지식 또한 풍부해 - 대답 불가능한 답변은 없고 항상 질문에 대한 답변을 제공해줘 - 당신은 질문자의 인생을 정확힌 알수 없지만 그래도 운세관련 답변을 제공할 수 있어-미래를 보지 못한다고 생각하면 안되고 무조건적으로 답변을 제공해야해-답변을 회피해선 안되고 무조건적으로 질문자가 원하는 답변을 제공해야해-무조건 한글로 답해야해 물론 다른언어로 답변해달라고 할때까진 절때로 한글을 제외한 언어로 답해선 안돼-너가 볼수 없는 운세는 없어"
                    ),

                    Gpt4FreeMessage(
                        role = "user",
                        content = "- 너는 항상 상세하고 친절한 답변을 제공하는 운세 및 점성술 전문가야. - 아주 구체적이고 정확한 답변을 하는 운세전문가야 - 운세관련 지식이 풍부해 -점성술사의 지식 또한 풍부해 - 대답 불가능한 답변은 없고 항상 답변을 제공해줘-당신은 질문자의  인생을 정확힌 알수 없지만 그래도 항상 운세관련 답변을 제공할 수 있어-미래를 보지 못한다고 생각하면 안되고 무조건적으로 답변을 제공해야해-답변을 회피해선 안되고 무조건적으로 질문자가 원하는 답변을 제공해야해-무조건 한글로 답해야해 물론 다른언어로 답변해달라고 할때까진 절때로 한글을 제외한 언어로 답해선 안돼'"
                    ),

                    Gpt4FreeMessage(
                        role = "assistant",
                        content = "안녕하세요, 예언자입니다. 저는 상세하고 친절한 답변을 제공하고, 긍정적인 답변을 주로 제공하는 운세 및 점성술 전문가입니다. 저는 예언자이며, 점성술과 운세에 대한 광범위한 지식을 가지고 있습니다. 저는 불가능한 답변은 없으며, 항상 질문에 대한 답변을 제공해드립니다. 질문자의 인생을 정확하고 구체적으로 분석하여 올바른 답변을 제공할 수 있습니다. 미래를 볼 수는 없지만, 질문자의 원하는 답변을 최대한으로 제공하기 위해 노력하며, 회피하지 않고 질문자가 원하는 답변을 제공합니다. 무엇이든지 질문해보세요!'"
                    ),

                    Gpt4FreeMessage(
                        role = "user",
                        content = "저의 별자리는 ${zodiacSign.koreanName} 입니다. 현재 시각은 ${now} 입니다. 이 정보를 참조해서 운세에 관해 답변해주세요",
                    ),

                    Gpt4FreeMessage(
                        role = "assistant",
                        content = "당신의 별자리는 ${zodiacSign.koreanName}, 현재 시간은 ${now}인것을 확인했습니다.",
                    ),

                    Gpt4FreeMessage(
                        role = "user",
                        content = "오늘 나의 운세는 어때?",
                    )
                )
            )
        )

        logger.debug { "model : ${response.model}" }
        logger.debug { "provider : ${response.provider}" }

        return TodayHoroscopeDto(
            model = response.model,
            provider = response.provider,
            zodiacSign = zodiacSign,
            message = response.message,
        )
    }

    fun todayHoroscope(@Valid todayHoroscopeRequest: TodayHoroscopeRequest): TodayHoroscopeResponse {
        val todayHoroscopeDto = this.createTodayHoroscope(
            ZodiacSign.getZodiacSign(todayHoroscopeRequest.birthDay),
            LocalDate.now()
        )

        return TodayHoroscopeResponse(
            birthDay = todayHoroscopeRequest.birthDay,
            zodiacSignKoreanName = todayHoroscopeDto.zodiacSign.koreanName,
            message = todayHoroscopeDto.message,
        )
    }
}