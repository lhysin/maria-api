package io.lhysin.common.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(HttpStatus.BAD_REQUEST)
class BadRequestException(

    /* 응답 메세지 */
    override val message: String,

    ) : RuntimeException(message)