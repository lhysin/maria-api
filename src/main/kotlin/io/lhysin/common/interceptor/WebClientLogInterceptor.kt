package io.lhysin.common.interceptor

import io.lhysin.common.handler.ActiveProfileHandler
import mu.KotlinLogging
import org.springframework.core.io.buffer.DataBuffer
import org.springframework.core.io.buffer.DataBufferUtils
import org.springframework.web.reactive.function.BodyExtractors
import org.springframework.web.reactive.function.client.ClientRequest
import org.springframework.web.reactive.function.client.ClientResponse
import org.springframework.web.reactive.function.client.ExchangeFilterFunction
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.nio.charset.StandardCharsets

private val logger = KotlinLogging.logger {}

class WebClientLogInterceptor {

    companion object {
        fun logRequest(): ExchangeFilterFunction {
            return ExchangeFilterFunction.ofRequestProcessor { clientRequest: ClientRequest ->
                if (logger.isDebugEnabled) {
                    logger.debug("Request: " + clientRequest.method() + " " + clientRequest.url())
                }
                Mono.just<ClientRequest>(clientRequest)
            }
        }

        fun logResponse(): ExchangeFilterFunction {
            return ExchangeFilterFunction.ofResponseProcessor { clientResponse: ClientResponse ->
                if (logger.isDebugEnabled) {
                    logger.debug("Response Status: {}", clientResponse.statusCode())
                }
                Mono.just<ClientResponse>(clientResponse)
            }
        }

        fun logResponse(activeProfileHandler: ActiveProfileHandler): ExchangeFilterFunction {
            return ExchangeFilterFunction.ofResponseProcessor { clientResponse: ClientResponse ->
                if (logger.isDebugEnabled) {
                    logger.debug("Response Status: {}", clientResponse.statusCode())
                }
                if (activeProfileHandler.isTestProfile() || activeProfileHandler.isDefaultProfile()) {
                    return@ofResponseProcessor DataBufferUtils.join(clientResponse.body<Flux<DataBuffer>>(BodyExtractors.toDataBuffers()))
                        .flatMap<ClientResponse> { dataBuffer: DataBuffer ->
                            val responseBody = StandardCharsets.UTF_8.decode(dataBuffer.asByteBuffer()).toString()
                            logger.debug("Response Body : {}", responseBody)
                            val cachedFlux = Flux.just(dataBuffer)
                            Mono.just<ClientResponse>(clientResponse.mutate().body(cachedFlux).build())
                        }
                } else {
                    return@ofResponseProcessor Mono.just<ClientResponse>(clientResponse)
                }
            }
        }
    }
}