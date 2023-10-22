package io.prince.kotlinspringbackend

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class KotlinSpringBackendApplication

fun main(args: Array<String>) {
    runApplication<KotlinSpringBackendApplication>(*args)
}
