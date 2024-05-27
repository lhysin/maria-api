package io.lhysin.common.advice

import io.lhysin.common.exception.AlreadyExistsResourceException
import io.lhysin.common.exception.BadRequestException
import io.lhysin.common.exception.ResourceNotFoundException
import io.lhysin.common.model.res.ErrorRes
import mu.KotlinLogging
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestController

private val logger = KotlinLogging.logger {}

@RestController
@ControllerAdvice(basePackages = ["io.lhysin.domain"])
class CustomCustomExceptionHandlerControllerAdvice {

    @ExceptionHandler(ResourceNotFoundException::class)
    fun handleResourceNotFoundException(ex: ResourceNotFoundException): ResponseEntity<ErrorRes> {

        io.lhysin.common.advice.logger.error(
            """
           CustomExceptionHandlerControllerAdvice handleResourceNotFoundException()
           ${ex.clazz.name} Not Found. id : [${ex.id}]
           ex.message : ${ex.message}
        """, ex
        )

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
            .body(
                ErrorRes(
                    message = ex.message
                )
            )
    }

    @ExceptionHandler(AlreadyExistsResourceException::class)
    fun handleAlreadyExistsResourceException(ex: AlreadyExistsResourceException): ResponseEntity<ErrorRes> {

        io.lhysin.common.advice.logger.error(
            """
           CustomExceptionHandlerControllerAdvice handleAlreadyExistsResourceException()
           ${ex.clazz.name} Already Exists. id : [${ex.id}]
           ex.message : ${ex.message}
        """, ex
        )

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(
                ErrorRes(
                    message = ex.message
                )
            )
    }

    @ExceptionHandler(BadRequestException::class)
    fun handleBadRequestException(ex: BadRequestException): ResponseEntity<ErrorRes> {

        io.lhysin.common.advice.logger.error(
            """
           CustomExceptionHandlerControllerAdvice handleBadRequestException()
           ex.message : ${ex.message}
        """, ex
        )

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(
                ErrorRes(
                    message = ex.message
                )
            )
    }
}