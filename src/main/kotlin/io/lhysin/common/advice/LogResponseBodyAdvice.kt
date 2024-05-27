package io.lhysin.common.advice

import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.servlet.ServletException
import jakarta.servlet.http.HttpServletRequest
import mu.KotlinLogging
import org.apache.commons.lang3.builder.ReflectionToStringBuilder
import org.springframework.core.MethodParameter
import org.springframework.http.MediaType
import org.springframework.http.converter.HttpMessageConverter
import org.springframework.http.server.ServerHttpRequest
import org.springframework.http.server.ServerHttpResponse
import org.springframework.http.server.ServletServerHttpRequest
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.filter.CommonsRequestLoggingFilter
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice

private val logger = KotlinLogging.logger {}

@ControllerAdvice(basePackages = ["io.lhysin.domain"])
class LogResponseBodyAdvice(
    private val objectMapper: ObjectMapper
) : CommonsRequestLoggingFilter(), ResponseBodyAdvice<Any> {

    @Throws(ServletException::class)
    override fun afterPropertiesSet() {
        super.afterPropertiesSet()
        super.setIncludeQueryString(true)
        super.setIncludeClientInfo(true)
        super.setIncludeHeaders(true)
        super.setIncludePayload(true)
    }

    override fun shouldLog(request: HttpServletRequest): Boolean {
        return false
    }

    override fun supports(
        returnType: MethodParameter,
        converterType: Class<out HttpMessageConverter<*>>
    ): Boolean {
        return true
    }

    override fun beforeBodyWrite(
        body: Any?,
        returnType: MethodParameter,
        selectedContentType: MediaType,
        selectedConverterType: Class<out HttpMessageConverter<*>>,
        request: ServerHttpRequest,
        response: ServerHttpResponse
    ): Any? {

        val servletRequest = (request as ServletServerHttpRequest).servletRequest

        if (servletRequest.requestURI == "/api/v1/auth/login") {
            return body
        }

        val requestMessage = super.createMessage(servletRequest, "Request : [", "]")
        val strBody: String = try {
            objectMapper.writeValueAsString(body)
        } catch (e: JsonProcessingException) {
            ReflectionToStringBuilder.toString(body)
        }

        logger.info(
            """
            $requestMessage
            Response Body : $strBody
            """.trimIndent()
        )

        return body
    }
}