package io.prince.kotlinspringbackend.domain.service

import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service

@Service
interface FastApiService {
    fun fetchStockData(ticker: String): ResponseEntity<String>
}