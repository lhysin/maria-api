package io.lhysin.common.interceptor

import io.lhysin.common.annotaion.AdminRole
import io.lhysin.common.annotaion.UserRole
import io.lhysin.common.component.ActiveUserHolder
import io.lhysin.common.component.InterceptorSupport
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import mu.KotlinLogging
import org.springframework.web.method.HandlerMethod
import org.springframework.web.servlet.HandlerInterceptor

private val logger = KotlinLogging.logger {}

class UserRoleInterceptor : HandlerInterceptor {

    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {

        // 인증 우회 케이스
        if (InterceptorSupport.isPermitted(request)) {
            return true
        }

        val activeUser = ActiveUserHolder.get()

        val handlerMethod = handler as? HandlerMethod
        return if (handlerMethod != null && !this.isUserRoleAllowed(activeUser.userRole, handlerMethod)) {
            logger.trace("접근이 거부되었습니다. 로그인 사용자: {}", activeUser)

            response.sendError(HttpServletResponse.SC_FORBIDDEN)
            false
        } else {
            true
        }
    }

    private fun isUserRoleAllowed(userRole: UserRole, handlerMethod: HandlerMethod): Boolean {

        // @AdminRole 존재, UserRole.ADMIN 아닌 경우.
        if (UserRole.ADMIN != userRole && handlerMethod.hasMethodAnnotation(AdminRole::class.java)) {
            logger.trace("관리자만 이 리소스에 접근할 수 있습니다.")
            return false
        }

        return true
    }
}