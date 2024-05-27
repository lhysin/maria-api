package io.lhysin.common.model.req

import com.fasterxml.jackson.annotation.JsonProperty
import io.lhysin.common.model.type.StringType
import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.Positive
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort

open class PageReq(

        @Schema(title = "페이지 번호", defaultValue = "0")
        @field:Positive
        val page: Int,

        @Schema(title = "페이지 크기", defaultValue = "10")
        @field:Positive
        val size: Int,

        @Schema(title = "정렬", description = "[age,-name]")
        val sort: List<String> = mutableListOf()
) {

    @get:JsonProperty("sort")
    val sorted: Sort
        get() {
            val sortOrders = this.sort.map { property ->
                val direction = if (property.startsWith(StringType.DASH.code)) {
                    Sort.Direction.DESC
                } else {
                    Sort.Direction.ASC
                }
                val column = property.removePrefix(StringType.DASH.code)
//                if (!getOrderColumnList().contains(column.uppercase())) {
                Sort.Order(direction, column)
//                } else {
//                    null
//                }
            }
            return Sort.by(sortOrders)
        }

    open fun getOrderColumnList(): List<String> {
        return emptyList()
    }

    fun createPageable(): Pageable {

        if (this.page < 1) {
            throw IllegalArgumentException("page Is Invalid.")
        }

        if (this.size < 1) {
            throw IllegalArgumentException("page Is Invalid.")
        }

        return PageRequest.of(
                // 사용자가 인지한 첫페이지는 1
                this.page - 1,
                this.size,
                this.sorted
        )
    }
}