package io.lhysin.common.component

import io.lhysin.common.annotaion.UserRole
import org.springframework.http.HttpStatus
import org.springframework.web.server.ResponseStatusException

object ActiveUserHolder {
    private val threadLocal = ThreadLocal<ActiveUser>()

    fun set(activeUser: ActiveUser) {
        threadLocal.set(activeUser)
    }

    fun get(): ActiveUser {
        return threadLocal.get()
            ?: anonymous()
    }

    fun clear() {
        threadLocal.remove()
    }

    @Throws(ResponseStatusException::class)
    fun ensureByUserId(userId: String) {
        if (this.get().userId != userId) {
            throw ResponseStatusException(HttpStatus.FORBIDDEN)
        }
    }

    fun anonymous(): ActiveUser {
        return ActiveUser(
            userId = UserRole.ANONYMOUS.name.lowercase(),
            userRole = UserRole.ANONYMOUS
        )
    }

}