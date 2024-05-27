package io.lhysin.common.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(HttpStatus.NOT_FOUND)
class ResourceNotFoundException(

    /* 서버 로깅을 위한 Class 정보 */
    val clazz: Class<*>,

    /* 서버 로깅을 위한 id 정보 */
    val id: Any,

    /* 응답 메세지 */
    override val message: String,

    ) : RuntimeException(message)