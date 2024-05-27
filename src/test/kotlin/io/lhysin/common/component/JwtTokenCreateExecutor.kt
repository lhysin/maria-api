package io.lhysin.common.component

import com.fasterxml.jackson.databind.ObjectMapper
import io.lhysin.common.annotaion.UserRole
import io.lhysin.common.provider.JwtTokenProvider
import mu.KotlinLogging
import org.junit.jupiter.api.Test

private val logger = KotlinLogging.logger {}

class JwtTokenCreateExecutor {

    private val jwtTokenProvider: JwtTokenProvider = JwtTokenProvider(ObjectMapper(), secret = "cY6CNWH8qkzq1oULxQwezFNh9j/F+61EpJp7eynMqGw=")

    @Test
    fun generateAdminAccessToken() {
        val token = jwtTokenProvider.generateAccessTokenByLoginUser(
            ActiveUser(
                userId = "admin",
                userRole = UserRole.ADMIN,
            )
        )

        logger.debug(
            """
            generated Token :
            $token
        """.trimIndent()
        )
    }

    // default profile :
    // Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ7XCJ1c2VySWRcIjpcImFkbWluXCIsXCJ1c2VyUm9sZVwiOlwiQURNSU5cIn0iLCJpYXQiOjE3MTY1MzI0ODQsImV4cCI6OTIyMzM3MjAzNjg1NDc3NX0.W4R2kMWa241cJxtTmGesm2E1eO2_jvO2tcYZU86aCEY
    @Test
    fun generateUnExpiredAdminAccessToken() {

        val token = jwtTokenProvider.generateUnExpiredAdminAccessToken(
            ActiveUser(
                userId = "admin",
                userRole = UserRole.ADMIN,
            )
        )

        logger.debug(
            """
            generated Token :
            $token
        """.trimIndent()
        )
    }
}