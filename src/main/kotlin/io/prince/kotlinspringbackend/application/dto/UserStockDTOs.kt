package io.prince.kotlinspringbackend.application.dto

class UserStockDTOs{
    data class GetResult(
        val id: String,
        val email: String,
        val ticker: String
    )

    data class PostRequest(
        val ticker: String
    )
}