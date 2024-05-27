package io.lhysin.external.client

import io.lhysin.external.model.response.JsonPost
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient

@Component
class JsonPlaceholderClient(
    @Value("\${external.jsonplaceholder.domain}")
    private val baseUrl: String
) {

    private val webClient = WebClient.create(baseUrl)

    fun getPost(id: Int) = webClient.get()
        .uri("/posts/{id}", id)
        .retrieve()
        .bodyToMono(JsonPost::class.java)

    fun getAllPosts() = webClient.get()
        .uri("/posts")
        .retrieve()
        .bodyToFlux(JsonPost::class.java)
}