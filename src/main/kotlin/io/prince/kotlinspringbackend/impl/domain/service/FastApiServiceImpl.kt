package io.prince.kotlinspringbackend.impl.domain.service

import io.prince.kotlinspringbackend.domain.service.FastApiService
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate

@Service
class FastApiServiceImpl(
    @Value("\${data.url}")
    private val dataUrl: String,
    private val restTemplate: RestTemplate
): FastApiService {
    override fun fetchStockData(
        ticker: String
    ): ResponseEntity<String> {
        val url = "${dataUrl}/stock/$ticker"
        val responseType = String::class.java
        return restTemplate.getForEntity(url, responseType)
    }

    override fun validateStock(ticker: String): ResponseEntity<Boolean> {
        val url = "${dataUrl}/validate/$ticker"
        val responseType = Boolean::class.java
        return restTemplate.getForEntity(url, responseType)
    }
}