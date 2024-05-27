package io.lhysin.common.model.type

interface Formatter<T, U> {
    fun format(t: T): U
}
