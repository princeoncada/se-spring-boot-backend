package io.prince.kotlinspringbackend.application.rest

import io.prince.kotlinspringbackend.application.dto.StockDTOs
import io.prince.kotlinspringbackend.domain.model.Stock
import io.prince.kotlinspringbackend.domain.service.StockService
import io.prince.kotlinspringbackend.domain.service.StockStatisticsService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/stock")
class StockController(
    private val stockService: StockService,
    private val stockStatisticsService: StockStatisticsService
) {
    @GetMapping("/{ticker}")
    fun getStock(
        @PathVariable ticker: String
    ): ResponseEntity<Stock> {
        return try {
            val stock = stockService.getStockByTicker(ticker)
            ResponseEntity.ok(stock)
        } catch (e: Exception) {
            ResponseEntity.notFound().build()
        }
    }

    @GetMapping
    fun getStockByParams(
    ): ResponseEntity<Stock> {
        return try {
            val stock = stockService.getStockByTicker("GOOG")
            ResponseEntity.ok(stock)
        } catch (e: Exception) {
            ResponseEntity.notFound().build()
        }
    }

    @GetMapping("/top")
    fun getTopStocks(): ResponseEntity<List<StockDTOs.GetResult>> {
        return try {
            val stocks = stockService.getTopStocks()
            ResponseEntity.ok(stocks)
        } catch (e: Exception) {
            ResponseEntity.notFound().build()
        }
    }

    @GetMapping("/minmax")
    fun getMinMaxValues(): ResponseEntity<MutableMap<String, DoubleArray>> {
        return try {
            val minMaxValues = stockService.getMinMaxValues()
            ResponseEntity.ok(minMaxValues)
        } catch (e: Exception) {
            ResponseEntity.notFound().build()
        }
    }

    @PostMapping("/filter")
    fun filterStocks(@RequestBody body: Map<String, List<Double>>): ResponseEntity<List<Stock>> {
        return try {
            val stocks = mutableListOf<Stock>()
            val data = stockStatisticsService.findStocksWithDynamicFilters(body)
            data.forEach { stocks.add(it.stock) }
            ResponseEntity.ok(stocks)
        } catch (e: Exception) {
            ResponseEntity.notFound().build()
        }
    }
}