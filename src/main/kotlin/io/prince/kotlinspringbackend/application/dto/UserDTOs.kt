package io.prince.kotlinspringbackend.application.dto

class UserDTOs {
    data class GetResult(
        val id: String,
        val role: String,
        val email: String,
        val password: String
    )

    data class PostRequest(
        val role: String,
        val email: String,
        val password: String
    )

    data class PutRequest(
        val role: String?,
        val email: String?,
        val password: String?
    )
}