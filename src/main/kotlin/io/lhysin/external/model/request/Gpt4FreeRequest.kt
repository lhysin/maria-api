package io.lhysin.external.model.request
data class Gpt4FreeRequest (
    // 공백인 경우 랜덤으로 모델을 선택
    val model: String = "",
    val provider: String = "",
    val max_tokens: Int? = null,
    val temperature: Double? = null,
    val top_p: Double? = null,
    val stream: Boolean = true,
    val messages: List<Gpt4FreeMessage>,

    )

data class Gpt4FreeMessage(
    val role: String = "user",
    val content: String,
)