package io.lhysin.common.model.type

import io.lhysin.common.model.constants.DateConstants
import java.time.LocalDate
import java.time.format.DateTimeFormatter

/**
 * The enum Local date formatter type.
 */
enum class LocalDateFormatterType(
        pattern: String
) : Formatter<LocalDate, String>, Parser<String, LocalDate> {
    /**
     * The Yyyy mm dd with DASH delimiter.
     * e.g. 2023-01-23
     */
    YYYY_MM_DD_DELIMITER_DASH(DateConstants.YYYY_MM_DD_DELIMITER_DASH),
    ;

    /**
     * Gets date time formatter.
     *
     * @return the date time formatter
     */
    val dateTimeFormatter: DateTimeFormatter

    init {
        dateTimeFormatter = DateTimeFormatter.ofPattern(pattern)
    }

    override fun format(t: LocalDate): String {
        return dateTimeFormatter.format(t)
    }

    override fun parse(t: String): LocalDate {
        return LocalDate.parse(t, dateTimeFormatter)
    }
}
