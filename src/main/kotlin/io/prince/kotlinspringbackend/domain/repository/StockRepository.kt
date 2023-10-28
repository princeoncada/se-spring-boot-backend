package io.prince.kotlinspringbackend.domain.repository

import io.prince.kotlinspringbackend.domain.model.Stock
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface StockRepository: JpaRepository<Stock, String> {
    fun findByTicker(ticker: String): Optional<Stock>
    fun existsByTicker(ticker: String): Boolean
}