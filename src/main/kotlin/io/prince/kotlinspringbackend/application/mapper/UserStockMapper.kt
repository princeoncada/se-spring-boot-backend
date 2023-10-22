package io.prince.kotlinspringbackend.application.mapper

import io.prince.kotlinspringbackend.application.dto.UserStockDTOs
import io.prince.kotlinspringbackend.domain.model.UserStock
import io.prince.kotlinspringbackend.domain.repository.StockRepository
import io.prince.kotlinspringbackend.domain.repository.UserRepository
import org.springframework.stereotype.Component

@Component
class UserStockMapper(
    private val userRepository: UserRepository,
    private val stockRepository: StockRepository,
) {
    fun toGetResult(
        entity: UserStock
    ): UserStockDTOs.GetResult {
        return UserStockDTOs.GetResult(
            id = entity.id,
            email = entity.user.email,
            ticker = entity.stock.ticker,
        )
    }

    fun createEntity(
        id: String,
        email: String,
        entityRequest: UserStockDTOs.PostRequest
    ): UserStock {
        return UserStock(
            id = id,
            ticker = entityRequest.ticker,
            user = userRepository.findByEmail(email).get(),
            stock = stockRepository.findByTicker(entityRequest.ticker).get(),
        )
    }
}