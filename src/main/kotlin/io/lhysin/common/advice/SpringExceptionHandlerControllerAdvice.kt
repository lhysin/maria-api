package io.lhysin.common.advice

import io.lhysin.common.model.res.ErrorRes
import mu.KotlinLogging
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestController

private val logger = KotlinLogging.logger {}

@RestController
@ControllerAdvice(basePackages = ["io.lhysin.domain"])
class SpringExceptionHandlerControllerAdvice {

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleMethodArgumentNotValidException(ex: MethodArgumentNotValidException): ResponseEntity<ErrorRes> {

        logger.error(
            """
           SpringExceptionHandlerControllerAdvice handleMethodArgumentNotValidException()
           ex.message : ${ex.message}
        """, ex
        )

        val errors = ex.bindingResult.fieldErrors.joinToString { fieldError ->
            "${fieldError.field}: ${fieldError.defaultMessage}"
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(
                ErrorRes(
                    message = errors
                )
            )
    }

}