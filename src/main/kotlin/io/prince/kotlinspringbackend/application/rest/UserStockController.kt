package io.prince.kotlinspringbackend.application.rest

import io.prince.kotlinspringbackend.application.dto.StockDTOs
import io.prince.kotlinspringbackend.application.dto.UserStockDTOs
import io.prince.kotlinspringbackend.domain.service.UserStockService
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/user/stock")
class UserStockController(
    private val userStockService: UserStockService
) {
    @GetMapping
    fun getStocksByUser(): ResponseEntity<List<StockDTOs.GetResult>> {
        return try {
            val authentication: Authentication = SecurityContextHolder.getContext().authentication
            val userStocks = userStockService.getStocksByUser(authentication.name)
            ResponseEntity.ok(userStocks)
        } catch (e: Exception) {
            ResponseEntity.badRequest().build()
        }
    }

    @GetMapping("/{ticker}")
    fun hasStock(
        @PathVariable ticker: String
    ): ResponseEntity<Boolean> {
        return try {
            val authentication: Authentication = SecurityContextHolder.getContext().authentication
            val hasStock = userStockService.hasStock(authentication.name, ticker)
            ResponseEntity.ok(hasStock)
        } catch (e: Exception) {
            ResponseEntity.badRequest().build()
        }
    }

    @PostMapping
    fun addStockToUser(
        @RequestBody requestEntity: UserStockDTOs.PostRequest
    ): ResponseEntity<UserStockDTOs.GetResult> {
        return try {
            val authentication: Authentication = SecurityContextHolder.getContext().authentication
            val userStock = userStockService.addStockToUser(authentication.name, requestEntity)
            ResponseEntity.ok(userStock)
        } catch (e: Exception) {
            ResponseEntity.badRequest().build()
        }
    }

    @DeleteMapping("/{ticker}")
    fun deleteStockFromUser(
        @PathVariable ticker: String
    ): ResponseEntity<List<UserStockDTOs.GetResult>> {
        return try {
            val authentication: Authentication = SecurityContextHolder.getContext().authentication
            val userStocks = userStockService.deleteStockFromUser(authentication.name, ticker)
            ResponseEntity.ok(userStocks)
        } catch (e: Exception) {
            ResponseEntity.badRequest().build()
        }
    }
}