package io.prince.kotlinspringbackend.application.dto

class StockDTOs {
    data class GetResult(
        val id: String,
        val ticker: String,
        val name: String,
        val price: Double,
        val growth: Int,
        val dividend: Int,
        val value: Int,
        val total: Int
    )

    data class PutRequest(
        val name: String,
        val stockData: String,
        val price: Double,
        val growth: Int,
        val dividend: Int,
        val value: Int,
        val total: Int,
        val expiry: String
    )

    data class PostRequest(
        val name: String,
        val ticker: String,
        val stockData: String,
        val price: Double,
        val growth: Int,
        val dividend: Int,
        val value: Int,
        val total: Int,
        val expiry: String
    )
}