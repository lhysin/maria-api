package io.lhysin.common.model.type

interface Parser<T, U> {
    fun parse(t: T): U
}
