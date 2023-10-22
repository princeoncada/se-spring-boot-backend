package io.prince.kotlinspringbackend.application.dto

class StockStatisticsDTOs {
    data class GetResult(
        val id: String,
        val ticker: String,
        val stockId: String,
        val trailingPe: Double,
        val priceSales: Double,
        val priceBook: Double,
        val enterpriseValueEbitda: Double,
        val returnOnEquity: Double,
        val quarterlyRevenueGrowth: Double,
        val quarterlyEarningsGrowth: Double,
        val totalDebtEquity: Double,
        val forwardAnnualDividendYield: Double,
        val trailingAnnualDividendYield: Double,
        val payoutRatio: Double,
        val interestCoverageRatio: Double,
        val operatingCashFlowNetIncomeRatio: Double,
        val freeCashFlowConversion: Double,
        val debtCoverageRatio: Double,
    )

    data class PostRequest(
        val ticker: String,
        val stockId: String,
        val trailingPe: Double,
        val priceSales: Double,
        val priceBook: Double,
        val enterpriseValueEbitda: Double,
        val returnOnEquity: Double,
        val quarterlyRevenueGrowth: Double,
        val quarterlyEarningsGrowth: Double,
        val totalDebtEquity: Double,
        val forwardAnnualDividendYield: Double,
        val trailingAnnualDividendYield: Double,
        val payoutRatio: Double,
        val interestCoverageRatio: Double,
        val operatingCashFlowNetIncomeRatio: Double,
        val freeCashFlowConversion: Double,
        val debtCoverageRatio: Double
    )

    data class PutRequest(
        val stockId: String,
        val trailingPe: Double,
        val priceSales: Double,
        val priceBook: Double,
        val enterpriseValueEbitda: Double,
        val returnOnEquity: Double,
        val quarterlyRevenueGrowth: Double,
        val quarterlyEarningsGrowth: Double,
        val totalDebtEquity: Double,
        val forwardAnnualDividendYield: Double,
        val trailingAnnualDividendYield: Double,
        val payoutRatio: Double,
        val interestCoverageRatio: Double,
        val operatingCashFlowNetIncomeRatio: Double,
        val freeCashFlowConversion: Double,
        val debtCoverageRatio: Double,
    )
}