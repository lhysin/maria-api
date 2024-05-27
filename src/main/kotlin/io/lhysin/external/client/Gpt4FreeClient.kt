package io.lhysin.external.client

import com.fasterxml.jackson.databind.ObjectMapper
import io.lhysin.common.handler.ActiveProfileHandler
import io.lhysin.common.interceptor.WebClientLogInterceptor
import io.lhysin.external.model.request.Gpt4FreeMessage
import io.lhysin.external.model.request.Gpt4FreeRequest
import io.lhysin.external.model.response.Gpt4FreeResponse
import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient

private val logger = KotlinLogging.logger {}

@Component
class Gpt4FreeClient(

    private val objectMapper: ObjectMapper,

    private val activeProfileHandler: ActiveProfileHandler,

    @Value("\${external.gpt-4-free.domain}")
    private val baseUrl: String
) {

    private val webClient = WebClient.builder()
        .baseUrl(baseUrl)
        .filter(WebClientLogInterceptor.logRequest())
        .filter(WebClientLogInterceptor.logResponse(activeProfileHandler))
        .build()

    fun completions(gpt4FreeRequest: Gpt4FreeRequest): Gpt4FreeResponse {
        val responseChunks = webClient.post()
            .uri("/v1/chat/completions")
            .bodyValue(gpt4FreeRequest)
            .retrieve()
            .bodyToFlux(String::class.java)
            .collectList()
            .block() ?: emptyList()

        return parseResponse(responseChunks)
    }

    fun chat(message: String): Gpt4FreeResponse {
        val responseChunks = webClient.post()
            .uri("/v1/chat/completions")
            .bodyValue(
                Gpt4FreeRequest(
                    messages = listOf(
                        Gpt4FreeMessage(
                            content = message
                        )
                    )
                )
            )
            .retrieve()
            .bodyToFlux(String::class.java)
            .collectList()
            .block() ?: emptyList()

        return parseResponse(responseChunks)
    }

    fun parseResponse(responseChunks: List<String>): Gpt4FreeResponse {
        val contents = mutableListOf<String>()
        var model = ""
        var provider = ""

        responseChunks.forEach { chunk ->
            if (chunk == "[DONE]") return@forEach

            try {
                val jsonNode = objectMapper.readTree(chunk)
                model = jsonNode["model"]?.asText() ?: model
                provider = jsonNode["provider"]?.asText() ?: provider

                val content = jsonNode["choices"]?.get(0)?.get("delta")?.get("content")?.asText()
                if (content != null) {
                    contents.add(content)
                }
            } catch (e: Exception) {
                logger.error("Error parsing chunk: ${e.message}", e)
            }
        }

        if (model.isEmpty() && provider.isEmpty() && contents.isEmpty()) {
            logger.error("No valid data found in response chunks")
            return Gpt4FreeResponse("", "", "Please try again.")
        }

        val message = contents.joinToString(separator = "")
        return Gpt4FreeResponse(model, provider, message)
    }


}

