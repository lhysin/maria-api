package io.lhysin.common.model.res

import com.fasterxml.jackson.annotation.JsonInclude

data class ErrorRes (
    val message : String,
    @JsonInclude(JsonInclude.Include.NON_NULL)
    val code : String? = null,
)
