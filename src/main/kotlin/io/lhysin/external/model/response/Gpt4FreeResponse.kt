package io.lhysin.external.model.response

data class Gpt4FreeResponse(
    val model: String,
    val provider: String,
    val message: String,
)
