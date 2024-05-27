package io.lhysin.common.interceptor

import jakarta.servlet.ServletException
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import mu.KotlinLogging
import org.springframework.web.filter.CommonsRequestLoggingFilter
import org.springframework.web.method.HandlerMethod
import org.springframework.web.servlet.HandlerInterceptor

private val logger = KotlinLogging.logger {}

class RequestLoggingInterceptor : CommonsRequestLoggingFilter(), HandlerInterceptor {

    companion object {
        private const val CONTROLLER_PACKAGE = "io.lhysin.domain"
    }

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

    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {
        if (handler is HandlerMethod) {
            val controllerClassName: String = handler.beanType.name
            if (controllerClassName.startsWith(CONTROLLER_PACKAGE)) {
                val requestURI: String = request.requestURI
                if (requestURI != "/api/v1/auth/login") {
                    val requestMessage: String = super.createMessage(request, "Request : [", "]")
                    logger.info(
                        """
                    %s
                    """.trimIndent().format(requestMessage)
                    )
                }
            }
        }
        return true
    }
}