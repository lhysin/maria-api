package io.lhysin.common.model.type

import io.lhysin.common.model.constants.DateConstants
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

/**
 * The enum Local date time formatter type.
 */
enum class LocalDateTimeFormatterType(
        pattern: String
) : Formatter<LocalDateTime, String>, Parser<String, LocalDateTime> {

    /**
     * The Yyyy mm dd hh mm ss delimiter dot and colon.
     * e.g. 2021-11-31 23:59:59
     */
    YYYY_MM_DD_HH_MM_SS_DELIMITER_DASH_AND_COLON(DateConstants.YYYY_MM_DD_HH_MM_SS_DELIMITER_DASH_AND_COLON),

    /**
     * e.g. 2021_11_31_23_59_59
     */
    YYYY_MM_DD_HH_MM_SS_DELIMITER_UNDER_BAR_ONLY(DateConstants.YYYY_MM_DD_HH_MM_SS_DELIMITER_UNDER_BAR_ONLY),

    /**
     * e.g. _20211131_235959
     */
    _YYYYMMDD_HHMMSS_DELIMITER_UNDER_BAR_ONLY(DateConstants._YYYYMMDD_HHMMSS_DELIMITER_UNDER_BAR_ONLY);

    /**
     * Gets date time formatter.
     *
     * @return the date time formatter
     */
    val dateTimeFormatter: DateTimeFormatter

    init {
        dateTimeFormatter = DateTimeFormatter.ofPattern(pattern)
    }

    override fun format(t: LocalDateTime): String {
        return dateTimeFormatter.format(t)
    }

    override fun parse(t: String): LocalDateTime {
        return LocalDateTime.parse(t, dateTimeFormatter)
    }
}
