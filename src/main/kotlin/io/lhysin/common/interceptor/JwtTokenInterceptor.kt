package io.lhysin.common.interceptor

import io.lhysin.common.component.ActiveUserHolder
import io.lhysin.common.component.InterceptorSupport
import io.lhysin.common.handler.ActiveProfileHandler
import io.lhysin.common.provider.JwtTokenProvider
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpHeaders
import org.springframework.web.servlet.HandlerInterceptor

class JwtTokenInterceptor(
    private val jwtTokenProvider: JwtTokenProvider,
    private val activeProfileHandler: ActiveProfileHandler,
) : HandlerInterceptor {

    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {

        // 인증 우회 케이스
        if (InterceptorSupport.isPermitted(request)) {
            return true
        }

        val token = request.getHeader(HttpHeaders.AUTHORIZATION)
            ?.removePrefix("Bearer ")
            ?.trim()

        var isValidToken = !token.isNullOrEmpty()

        if (!token.isNullOrEmpty()) {
            try {
                val loginUser = jwtTokenProvider.extractUserIdFromToken(token)
                ActiveUserHolder.set(loginUser)
            } catch (ex: Exception) {
                isValidToken = false
            }
        }

        return if (isValidToken) {
            true
        } else {
            ActiveUserHolder.set(
                ActiveUserHolder.anonymous()
            )
            true
        }
    }

    override fun afterCompletion(
        request: HttpServletRequest,
        response: HttpServletResponse,
        handler: Any,
        ex: Exception?
    ) {
        ActiveUserHolder.clear()
    }

}