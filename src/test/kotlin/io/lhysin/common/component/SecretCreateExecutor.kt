package io.lhysin.common.component

import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.security.Keys
import mu.KotlinLogging
import org.junit.jupiter.api.Test
import java.util.*


private val logger = KotlinLogging.logger {}

class SecretCreateExecutor {

    @Test
    fun createSecretKey() {
        val secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256)
        val secret = Base64.getEncoder().encodeToString(secretKey.encoded)

        logger.debug(
            """
            encoded secret :
            $secret
        """.trimIndent()
        )
    }
}