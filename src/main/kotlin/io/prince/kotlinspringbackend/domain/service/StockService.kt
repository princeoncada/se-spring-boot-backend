package io.prince.kotlinspringbackend.domain.service

import io.prince.kotlinspringbackend.application.dto.StockDTOs
import io.prince.kotlinspringbackend.domain.model.Stock
import org.springframework.stereotype.Service

@Service
interface StockService {
    fun getStockByTicker(ticker: String): Stock
    fun getTopStocks(): List<StockDTOs.GetResult>
    fun isExpired(stock: Stock): Boolean
    fun getMinMaxValues(): MutableMap<String, DoubleArray>
    fun validateStock(ticker: String): Boolean
}