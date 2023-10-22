package io.prince.kotlinspringbackend.impl.domain.service

import io.prince.kotlinspringbackend.application.dto.StockDTOs
import io.prince.kotlinspringbackend.application.dto.UserStockDTOs
import io.prince.kotlinspringbackend.application.mapper.StockMapper
import io.prince.kotlinspringbackend.application.mapper.UserStockMapper
import io.prince.kotlinspringbackend.domain.model.Stock
import io.prince.kotlinspringbackend.domain.repository.UserStockRepository
import io.prince.kotlinspringbackend.domain.service.UserStockService
import org.springframework.stereotype.Service
import java.util.*

@Service
class UserStockServiceImpl(
    private val userStockRepository: UserStockRepository,
    private val userStockMapper: UserStockMapper,
    private val stockMapper: StockMapper
): UserStockService {
    override fun getStocksByUser(
        email: String
    ): List<StockDTOs.GetResult> {
        val stocks = mutableListOf<Stock>()
        val userStocks = userStockRepository.findByUserEmail(email)
        userStocks.forEach {
            stocks.add(it.stock)
        }
        return stocks.map { stockMapper.toGetResult(it) }
    }
    override fun addStockToUser(
        email: String,
        entityRequest: UserStockDTOs.PostRequest
    ): UserStockDTOs.GetResult {
        val userStockId = UUID.randomUUID().toString()
        val savedUserStock = userStockRepository.save(userStockMapper.createEntity(userStockId, email, entityRequest))
        return userStockMapper.toGetResult(savedUserStock)
    }

    override fun deleteStockFromUser(
        email: String,
        ticker: String
    ): List<UserStockDTOs.GetResult> {
        val currentUserStock = userStockRepository.findByUserEmailAndStockTicker(email, ticker).get()
        userStockRepository.delete(currentUserStock)
        return userStockRepository.findByUserEmail(email).map { userStockMapper.toGetResult(it) }
    }

    override fun hasStock(
        email: String,
        ticker: String
    ): Boolean {
        return userStockRepository.existsByUserEmailAndStockTicker(email, ticker)
    }
}