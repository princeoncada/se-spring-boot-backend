package io.prince.kotlinspringbackend.domain.service

import io.prince.kotlinspringbackend.application.dto.StockDTOs
import io.prince.kotlinspringbackend.application.dto.UserStockDTOs
import org.springframework.stereotype.Service

@Service
interface UserStockService {
    fun getStocksByUser(email: String): List<StockDTOs.GetResult>
    fun addStockToUser(email: String, entityRequest: UserStockDTOs.PostRequest): UserStockDTOs.GetResult
    fun deleteStockFromUser(email: String, ticker: String): List<UserStockDTOs.GetResult>
    fun hasStock(email: String, ticker: String): Boolean
}