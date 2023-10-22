package io.prince.kotlinspringbackend.application.mapper

import io.prince.kotlinspringbackend.application.dto.StockStatisticsDTOs
import io.prince.kotlinspringbackend.domain.model.StockStatistics
import io.prince.kotlinspringbackend.domain.repository.StockRepository
import org.springframework.stereotype.Component
import java.time.Instant

@Component
class StockStatisticsMapper(
    private val stockRepository: StockRepository
) {
    fun toGetResult(
        entity: StockStatistics
    ): StockStatisticsDTOs.GetResult {
        return StockStatisticsDTOs.GetResult(
            id = entity.id,
            ticker = entity.ticker,
            stockId = entity.stock.id,
            trailingPe = entity.trailingPe,
            priceSales = entity.priceSales,
            priceBook = entity.priceBook,
            enterpriseValueEbitda = entity.enterpriseValueEbitda,
            returnOnEquity = entity.returnOnEquity,
            quarterlyRevenueGrowth = entity.quarterlyRevenueGrowth,
            quarterlyEarningsGrowth = entity.quarterlyEarningsGrowth,
            totalDebtEquity = entity.totalDebtEquity,
            forwardAnnualDividendYield = entity.forwardAnnualDividendYield,
            trailingAnnualDividendYield = entity.trailingAnnualDividendYield,
            payoutRatio = entity.payoutRatio,
            interestCoverageRatio = entity.interestCoverageRatio,
            operatingCashFlowNetIncomeRatio = entity.operatingCashFlowNetIncomeRatio,
            freeCashFlowConversion = entity.freeCashFlowConversion,
            debtCoverageRatio = entity.debtCoverageRatio,
        )
    }

    fun createEntity(
        id: String,
        entityRequest: StockStatisticsDTOs.PostRequest
    ): StockStatistics {
        return StockStatistics(
            id = id,
            ticker = entityRequest.ticker,
            stock = stockRepository.findById(entityRequest.stockId).get(),
            trailingPe = entityRequest.trailingPe,
            priceSales = entityRequest.priceSales,
            priceBook = entityRequest.priceBook,
            enterpriseValueEbitda = entityRequest.enterpriseValueEbitda,
            returnOnEquity = entityRequest.returnOnEquity,
            quarterlyRevenueGrowth = entityRequest.quarterlyRevenueGrowth,
            quarterlyEarningsGrowth = entityRequest.quarterlyEarningsGrowth,
            totalDebtEquity = entityRequest.totalDebtEquity,
            forwardAnnualDividendYield = entityRequest.forwardAnnualDividendYield,
            trailingAnnualDividendYield = entityRequest.trailingAnnualDividendYield,
            payoutRatio = entityRequest.payoutRatio,
            interestCoverageRatio = entityRequest.interestCoverageRatio,
            operatingCashFlowNetIncomeRatio = entityRequest.operatingCashFlowNetIncomeRatio,
            freeCashFlowConversion = entityRequest.freeCashFlowConversion,
            debtCoverageRatio = entityRequest.debtCoverageRatio,
            createdAt = Instant.now(),
            updatedAt = Instant.now()
        )
    }

    fun updateEntity(
        entity: StockStatistics,
        entityRequest: StockStatisticsDTOs.PutRequest
    ): StockStatistics {
        return StockStatistics(
            id = entity.id,
            ticker = entity.ticker,
            stock = stockRepository.findById(entityRequest.stockId).get(),
            trailingPe = entityRequest.trailingPe,
            priceSales = entityRequest.priceSales,
            priceBook = entityRequest.priceBook,
            enterpriseValueEbitda = entityRequest.enterpriseValueEbitda,
            returnOnEquity = entityRequest.returnOnEquity,
            quarterlyRevenueGrowth = entityRequest.quarterlyRevenueGrowth,
            quarterlyEarningsGrowth = entityRequest.quarterlyEarningsGrowth,
            totalDebtEquity = entityRequest.totalDebtEquity,
            forwardAnnualDividendYield = entityRequest.forwardAnnualDividendYield,
            trailingAnnualDividendYield = entityRequest.trailingAnnualDividendYield,
            payoutRatio = entityRequest.payoutRatio,
            interestCoverageRatio = entityRequest.interestCoverageRatio,
            operatingCashFlowNetIncomeRatio = entityRequest.operatingCashFlowNetIncomeRatio,
            freeCashFlowConversion = entityRequest.freeCashFlowConversion,
            debtCoverageRatio = entityRequest.debtCoverageRatio,
            createdAt = entity.createdAt,
            updatedAt = Instant.now()
        )
    }
}