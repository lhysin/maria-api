package io.lhysin

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class MariaApiApplication

fun main(args: Array<String>) {
    runApplication<io.lhysin.MariaApiApplication>(*args)
}