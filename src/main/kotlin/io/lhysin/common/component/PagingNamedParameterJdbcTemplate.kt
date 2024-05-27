package io.lhysin.common.component

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.support.PageableExecutionUtils
import org.springframework.jdbc.core.RowMapper
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.stereotype.Component

@Component
class PagingNamedParameterJdbcTemplate(
    private val namedParameterJdbcTemplate: NamedParameterJdbcTemplate,
) {

    fun <T> queryForPaging(
        sql: String,
        params: MapSqlParameterSource,
        pageable: Pageable,
        rowMapper: RowMapper<T>,
    ): Page<T> {

        val trimIndentSql = sql.trimIndent()

        val countSql = """
            SELECT COUNT(*) FROM ($trimIndentSql) AS count_query
        """
        val totalCount = namedParameterJdbcTemplate.queryForObject(countSql, params, Long::class.java) ?: 0

        val pagingQuery = StringBuilder(trimIndentSql)
        if (pageable.sort.isSorted) {
            pagingQuery.append(" ORDER BY ")
            val sortColumns = pageable.sort.map { order -> "${order.property} ${order.direction}" }
            pagingQuery.append(sortColumns.joinToString(", "))
        }
        pagingQuery.append(" LIMIT :limit OFFSET :offset")

        params.addValue("limit", pageable.pageSize)
        params.addValue("offset", pageable.offset)

        val resultList = namedParameterJdbcTemplate.query(pagingQuery.toString(), params, rowMapper)

        return PageableExecutionUtils.getPage(
            resultList,
            pageable
        ) { totalCount }
    }
}