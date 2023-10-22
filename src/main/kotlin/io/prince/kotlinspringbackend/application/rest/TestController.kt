package io.prince.kotlinspringbackend.application.rest

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/test")
class TestController {
    @RequestMapping("/hello")
    fun hello(): ResponseEntity<String> {
        return try {
            ResponseEntity.ok("Hello World!")
        } catch (e: Exception) {
            ResponseEntity.notFound().build()
        }
    }
}