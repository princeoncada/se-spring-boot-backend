package io.prince.kotlinspringbackend.domain.repository

import io.prince.kotlinspringbackend.domain.model.UserStock
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface UserStockRepository: JpaRepository<UserStock, String> {
    fun findByUserEmail(email: String): List<UserStock>
    fun findByUserEmailAndStockTicker(email: String, ticker: String): Optional<UserStock>
    fun existsByUserEmailAndStockTicker(email: String, ticker: String): Boolean
}