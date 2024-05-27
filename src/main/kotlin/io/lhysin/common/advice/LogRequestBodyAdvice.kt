package io.lhysin.common.advice

import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.ObjectMapper
import mu.KotlinLogging
import org.apache.commons.lang3.builder.ReflectionToStringBuilder
import org.springframework.core.MethodParameter
import org.springframework.http.HttpInputMessage
import org.springframework.http.converter.HttpMessageConverter
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.context.request.RequestContextHolder
import org.springframework.web.context.request.ServletRequestAttributes
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdviceAdapter
import java.lang.reflect.Type

private val logger = KotlinLogging.logger {}

@ControllerAdvice(basePackages = ["io.lhysin.domain"])
class LogRequestBodyAdvice(
    private val objectMapper: ObjectMapper
) : RequestBodyAdviceAdapter() {

    override fun supports(
        methodParameter: MethodParameter,
        targetType: Type,
        converterType: Class<out HttpMessageConverter<*>>
    ): Boolean {
        return true
    }

    @Throws(Exception::class)
    override fun afterBodyRead(
        body: Any,
        inputMessage: HttpInputMessage,
        parameter: MethodParameter,
        targetType: Type,
        converterType: Class<out HttpMessageConverter<*>>
    ): Any {

        val requestURI: String =
            (RequestContextHolder.getRequestAttributes() as? ServletRequestAttributes)?.request?.requestURI ?: ""

        if (requestURI == "/api/v1/auth/login") {
            return body
        }

        val strBody: String = try {
            objectMapper.writeValueAsString(body)
        } catch (e: JsonProcessingException) {
            ReflectionToStringBuilder.toString(body)
        }

        io.lhysin.common.advice.logger.info(
            """
            Request Body : %s
            """.trimIndent().format(strBody)
        )

        return body
    }


}