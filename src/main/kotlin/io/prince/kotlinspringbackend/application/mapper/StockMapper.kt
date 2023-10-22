package io.prince.kotlinspringbackend.application.mapper

import io.prince.kotlinspringbackend.application.dto.StockDTOs
import io.prince.kotlinspringbackend.domain.model.Stock
import org.springframework.stereotype.Component
import java.time.Instant
import java.time.LocalDateTime

@Component
class StockMapper {
    fun toGetResult(
        entity: Stock
    ): StockDTOs.GetResult {
        return StockDTOs.GetResult(
            id = entity.id,
            ticker = entity.ticker,
            name = entity.name,
            price = entity.price,
            growth = entity.growth,
            dividend = entity.dividend,
            value = entity.value,
            total = entity.total,
        )
    }

    fun createEntity(
        id: String,
        entityRequest: StockDTOs.PostRequest
    ): Stock {
        return Stock(
            id = id,
            ticker = entityRequest.ticker,
            name = entityRequest.name,
            stockData = entityRequest.stockData,
            price = entityRequest.price,
            growth = entityRequest.growth,
            dividend = entityRequest.dividend,
            value = entityRequest.value,
            total = entityRequest.total,
            expiry = LocalDateTime.parse(entityRequest.expiry),
            createdAt = Instant.now(),
            updatedAt = Instant.now()
        )
    }

    fun updateEntity(
        entity: Stock,
        entityRequest: StockDTOs.PutRequest
    ): Stock {
        return Stock(
            id = entity.id,
            ticker = entity.ticker,
            name = entityRequest.name,
            stockData = entityRequest.stockData,
            price = entityRequest.price,
            growth = entityRequest.growth,
            dividend = entityRequest.dividend,
            value = entityRequest.value,
            total = entityRequest.total,
            expiry = LocalDateTime.parse(entityRequest.expiry),
            createdAt = entity.createdAt,
            updatedAt = Instant.now()
        )
    }
}