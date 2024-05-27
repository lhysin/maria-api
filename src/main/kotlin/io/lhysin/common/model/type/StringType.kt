package io.lhysin.common.model.type

enum class StringType(
    val code: String
) {
    BLANK(""),
    WHITE_SPACE(" "),
    DASH("-"),
    NEW_LINE("\n"),
    UNDER_SCORE("_"),
    LOG_FORMAT("%s"),
    ASTERISK("*"),
    COMMA(","),
    COLON(":"),
}