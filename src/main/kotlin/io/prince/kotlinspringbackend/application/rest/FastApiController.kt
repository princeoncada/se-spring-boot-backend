package io.prince.kotlinspringbackend.application.rest

import com.fasterxml.jackson.databind.ObjectMapper
import io.prince.kotlinspringbackend.domain.service.FastApiService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/fast")
class FastApiController(
    private val fastApiService: FastApiService,
    private val objectMapper: ObjectMapper
) {
    @GetMapping("/{ticker}")
    fun fetchStockData(
        @PathVariable ticker: String
    ): ResponseEntity<Map<*, *>> {
        val response = fastApiService.fetchStockData(ticker)
        val body = objectMapper.readValue(response.body, Map::class.java)
        return ResponseEntity.ok(body)
    }
}