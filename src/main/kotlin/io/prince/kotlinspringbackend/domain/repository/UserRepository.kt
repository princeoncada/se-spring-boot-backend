package io.prince.kotlinspringbackend.domain.repository

import io.prince.kotlinspringbackend.domain.model.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface UserRepository: JpaRepository<User, String> {
    fun findByEmail(email: String): Optional<User>
}