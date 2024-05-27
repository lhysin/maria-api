package io.lhysin.common.component

import jakarta.servlet.http.HttpServletRequest
import org.springframework.web.cors.CorsUtils

object InterceptorSupport {
    private val permittedPatterns = listOf(
        "/swagger-ui",
        "/v3/api-docs",
        "/actuator"
    )

    fun isPermitted(httpServletRequest: HttpServletRequest) : Boolean {
        // cors 검증
        return CorsUtils.isPreFlightRequest(httpServletRequest) ||
            // 인증 우회 URL
            isUrlPermitted(httpServletRequest.requestURI)
    }

    private fun isUrlPermitted(requestURI: String): Boolean {
        return permittedPatterns.any { requestURI.startsWith(it) }
    }
}