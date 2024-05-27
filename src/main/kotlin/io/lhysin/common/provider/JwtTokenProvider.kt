package io.lhysin.common.provider

import com.fasterxml.jackson.databind.ObjectMapper
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import io.lhysin.common.component.ActiveUser
import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import org.springframework.web.server.ResponseStatusException
import java.util.*
import javax.crypto.SecretKey

private val logger = KotlinLogging.logger {}

@Component
class JwtTokenProvider(
    private val objectMapper: ObjectMapper,

    @Value("\${maria-api.secret}")
    private var secret: String,
) {

    companion object {
        private const val ACCESS_TOKEN_VALIDITY_MS = 60 * 60 * 1000 // 60 minutes
        private const val REFRESH_TOKEN_VALIDITY_MS = 7 * 24 * 60 * 60 * 1000 // 7 days

        private const val ANONYMOUS_VALIDITY_MS = 24 * 60 * 60 * 1000 // 1 day
    }

    private lateinit var secretKey: SecretKey

    init {
        initializeSecretKey()
    }

    private fun initializeSecretKey() {
        val decodedKey = Base64.getDecoder().decode(secret)
        secretKey = Keys.hmacShaKeyFor(decodedKey)
        //secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256)
    }

    private fun secretKey(): SecretKey {
        val decodedKey = Base64.getDecoder().decode(secret)
        return Keys.hmacShaKeyFor(decodedKey)
    }

    fun generateAccessTokenByLoginUser(activeUser: ActiveUser): String {
        return Jwts.builder()
            .setSubject(
                objectMapper.writeValueAsString(activeUser)
            )
            .setIssuedAt(Date())
            .setExpiration(Date(System.currentTimeMillis() + ACCESS_TOKEN_VALIDITY_MS))
            .signWith(secretKey)
            .compact()
    }

    fun generateUnExpiredAdminAccessToken(activeUser: ActiveUser): String {
        return Jwts.builder()
            .setSubject(
                objectMapper.writeValueAsString(activeUser)
            )
            .setIssuedAt(Date())
            .setExpiration(Date(Long.MAX_VALUE))
            .signWith(secretKey)
            .compact()
    }

    fun generateRefreshTokenByLoginUser(activeUser: ActiveUser): String {
        return Jwts.builder()
            .setSubject(
                objectMapper.writeValueAsString(activeUser)
            )
            .setIssuedAt(Date())
            .setExpiration(Date(System.currentTimeMillis() + REFRESH_TOKEN_VALIDITY_MS))
            .signWith(secretKey)
            .compact()
    }

    @Throws(IllegalArgumentException::class)
    fun extractUserIdFromToken(token: String): ActiveUser {

        return try {

            if (token.isEmpty()) {
                throw ResponseStatusException(HttpStatus.FORBIDDEN, "Invalid Token.")
            }

            val claims = Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .body

            val expirationDate = claims.expiration

            if (expirationDate.before(Date())) {
                throw ResponseStatusException(HttpStatus.FORBIDDEN, "Invalid Token.")
            }

            objectMapper.readValue(claims.subject, ActiveUser::class.java)
        } catch (e: Exception) {
            logger.error(e.message, e)
            throw ResponseStatusException(HttpStatus.FORBIDDEN, "Invalid Token.")
        }
    }

}