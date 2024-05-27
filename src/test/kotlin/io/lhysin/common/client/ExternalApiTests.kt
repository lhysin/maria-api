package io.lhysin.common.client

import io.lhysin.external.client.Gpt4FreeClient
import io.lhysin.external.client.JsonPlaceholderClient
import mu.KotlinLogging
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles

private val logger = KotlinLogging.logger {}

@SpringBootTest(
    classes = [io.lhysin.MariaApiApplication::class],
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@ActiveProfiles("test")
class ExternalApiTests (
    @Autowired
    var jsonPlaceholderClient: JsonPlaceholderClient,
    @Autowired
    var gpt4FreeClient: Gpt4FreeClient,
) {

    @Test
    fun adminApiTest() {
        logger.debug(jsonPlaceholderClient.getPost(1).toString())
    }

    @Test
    fun gpt4freeTest() {
        logger.debug { gpt4FreeClient.chat("너에 대해 자세히 설명해줘").message }
    }

}