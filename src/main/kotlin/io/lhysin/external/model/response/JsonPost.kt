package io.lhysin.external.model.response

data class JsonPost(
    val id: Int,
    val title: String,
    val body: String,
    val userId: Int
)