package io.prince.kotlinspringbackend.impl.domain.service

import com.fasterxml.jackson.databind.ObjectMapper
import io.prince.kotlinspringbackend.application.dto.StockDTOs
import io.prince.kotlinspringbackend.application.mapper.StockMapper
import io.prince.kotlinspringbackend.domain.model.Stock
import io.prince.kotlinspringbackend.domain.model.StockStatistics
import io.prince.kotlinspringbackend.domain.repository.StockRepository
import io.prince.kotlinspringbackend.domain.repository.StockStatisticsRepository
import io.prince.kotlinspringbackend.domain.service.FastApiService
import io.prince.kotlinspringbackend.domain.service.StockService
import org.springframework.stereotype.Service
import java.time.Instant
import java.time.LocalDateTime
import java.util.*

@Service
class StockServiceImpl(
    private val fastApiService: FastApiService,
    private val stockRepository: StockRepository,
    private val stockStatisticsRepository: StockStatisticsRepository,
    private val stockMapper: StockMapper,
    private val objectMapper: ObjectMapper
): StockService {
    override fun getStockByTicker(ticker: String): Stock {
        val stock = stockRepository.findByTicker(ticker)
        if (stock.isPresent) {
            if (!isExpired(stock.get())){
                return stock.get()
            } else {
                try {
                    val stockStatistics = stockStatisticsRepository.findByTicker(ticker).get()
                    val stockData = fastApiService.fetchStockData(ticker).body!!
                    val stockJsonNode = objectMapper.readTree(stockData)

                    val newStock = stockMapper.createEntity(
                        id = stock.get().id,
                        entityRequest = StockDTOs.PostRequest(
                            ticker = stock.get().ticker,
                            name = stock.get().name,
                            stockData = stockData,
                            price = stockJsonNode["details"]["Price"].asDouble(),
                            growth = stockJsonNode["score"]["growth"].asInt(),
                            dividend = stockJsonNode["score"]["dividend"].asInt(),
                            value = stockJsonNode["score"]["value"].asInt(),
                            total = stockJsonNode["score"]["total"].asInt(),
                            expiry = LocalDateTime.now().plusDays(7).toString()
                        )
                    )

                    val newStockStatistics = StockStatistics(
                        id = stockStatistics.id,
                        ticker = stockStatistics.ticker,
                        stock = newStock,
                        trailingPe = stockJsonNode["statistics"]["Trailing P/E"].asDouble(),
                        priceSales = stockJsonNode["statistics"]["Price/Sales"].asDouble(),
                        priceBook = stockJsonNode["statistics"]["Price/Book"].asDouble(),
                        enterpriseValueEbitda = stockJsonNode["statistics"]["Enterprise Value/EBITDA"].asDouble(),
                        returnOnEquity = stockJsonNode["statistics"]["Return on Equity"].asDouble(),
                        quarterlyRevenueGrowth = stockJsonNode["statistics"]["Quarterly Revenue Growth"].asDouble(),
                        quarterlyEarningsGrowth = stockJsonNode["statistics"]["Quarterly Earnings Growth"].asDouble(),
                        totalDebtEquity = stockJsonNode["statistics"]["Total Debt/Equity"].asDouble(),
                        forwardAnnualDividendYield = stockJsonNode["statistics"]["Forward Annual Dividend Yield"].asDouble(),
                        trailingAnnualDividendYield = stockJsonNode["statistics"]["Trailing Annual Dividend Yield"].asDouble(),
                        payoutRatio = stockJsonNode["statistics"]["Payout Ratio"].asDouble(),
                        interestCoverageRatio = stockJsonNode["statistics"]["Interest Coverage Ratio"].asDouble(),
                        operatingCashFlowNetIncomeRatio = stockJsonNode["statistics"]["Operating Cash Flow / Net Income Ratio"].asDouble(),
                        freeCashFlowConversion = stockJsonNode["statistics"]["Free Cash Flow Conversion"].asDouble(),
                        debtCoverageRatio = stockJsonNode["statistics"]["Debt Coverage Ratio"].asDouble(),
                        createdAt = stockStatistics.createdAt,
                        updatedAt = Instant.now()
                    )

                    val savedStock = stockRepository.save(newStock)
                    stockStatisticsRepository.save(newStockStatistics)
                    return savedStock
                } catch (e: Exception) {
                    throw Exception("Error occurred during process stock is expired / Error: ${e.message}")
                }

            }
        } else {
            try {
                val stockData = fastApiService.fetchStockData(ticker).body!!
                val stockJsonNode = objectMapper.readTree(stockData)

                val newStock = stockMapper.createEntity(
                    id = UUID.randomUUID().toString(),
                    entityRequest = StockDTOs.PostRequest(
                        ticker = ticker,
                        name = stockJsonNode["details"]["Name"].asText(),
                        stockData = stockData,
                        price = stockJsonNode["details"]["Price"].asDouble(),
                        growth = stockJsonNode["score"]["growth"].asInt(),
                        dividend = stockJsonNode["score"]["dividend"].asInt(),
                        value = stockJsonNode["score"]["value"].asInt(),
                        total = stockJsonNode["score"]["total"].asInt(),
                        expiry = LocalDateTime.now().plusDays(7).toString()
                    )
                )

                val newStockStatistics = StockStatistics(
                    id = UUID.randomUUID().toString(),
                    ticker = ticker,
                    stock = newStock,
                    trailingPe = stockJsonNode["statistics"]["Trailing P/E"].asDouble(),
                    priceSales = stockJsonNode["statistics"]["Price/Sales"].asDouble(),
                    priceBook = stockJsonNode["statistics"]["Price/Book"].asDouble(),
                    enterpriseValueEbitda = stockJsonNode["statistics"]["Enterprise Value/EBITDA"].asDouble(),
                    returnOnEquity = stockJsonNode["statistics"]["Return on Equity"].asDouble(),
                    quarterlyRevenueGrowth = stockJsonNode["statistics"]["Quarterly Revenue Growth"].asDouble(),
                    quarterlyEarningsGrowth = stockJsonNode["statistics"]["Quarterly Earnings Growth"].asDouble(),
                    totalDebtEquity = stockJsonNode["statistics"]["Total Debt/Equity"].asDouble(),
                    forwardAnnualDividendYield = stockJsonNode["statistics"]["Forward Annual Dividend Yield"].asDouble(),
                    trailingAnnualDividendYield = stockJsonNode["statistics"]["Trailing Annual Dividend Yield"].asDouble(),
                    payoutRatio = stockJsonNode["statistics"]["Payout Ratio"].asDouble(),
                    interestCoverageRatio = stockJsonNode["statistics"]["Interest Coverage Ratio"].asDouble(),
                    operatingCashFlowNetIncomeRatio = stockJsonNode["statistics"]["Operating Cash Flow / Net Income Ratio"].asDouble(),
                    freeCashFlowConversion = stockJsonNode["statistics"]["Free Cash Flow Conversion"].asDouble(),
                    debtCoverageRatio = stockJsonNode["statistics"]["Debt Coverage Ratio"].asDouble(),
                    createdAt = Instant.now(),
                    updatedAt = Instant.now()
                )

                val savedStock = stockRepository.save(newStock)
                stockStatisticsRepository.save(newStockStatistics)
                return savedStock
            } catch (e: Exception) {
                throw Exception("Error occurred during process stock not present / Error: ${e.message}")
            }
        }
    }

    override fun getTopStocks(): List<StockDTOs.GetResult> {
        return stockRepository.findAll().sortedByDescending { it.total }.take(10).map { stockMapper.toGetResult(it) }
    }

    override fun isExpired(stock: Stock): Boolean {
        return stock.expiry.isBefore(LocalDateTime.now())
    }

    override fun getMinMaxValues(): MutableMap<String, DoubleArray> {
        val minMaxValues = stockStatisticsRepository.findMinMaxValues()
        val minMaxValuesMap = mutableMapOf<String, DoubleArray>()

        minMaxValuesMap["trailing_pe"] = doubleArrayOf(minMaxValues["min_trailing_pe"]!!.toDouble(), minMaxValues["max_trailing_pe"]!!.toDouble())
        minMaxValuesMap["price_sales"] = doubleArrayOf(minMaxValues["min_price_sales"]!!.toDouble(), minMaxValues["max_price_sales"]!!.toDouble())
        minMaxValuesMap["price_book"] = doubleArrayOf(minMaxValues["min_price_book"]!!.toDouble(), minMaxValues["max_price_book"]!!.toDouble())
        minMaxValuesMap["enterprise_value_ebitda"] = doubleArrayOf(minMaxValues["min_enterprise_value_ebitda"]!!.toDouble(), minMaxValues["max_enterprise_value_ebitda"]!!.toDouble())
        minMaxValuesMap["return_on_equity"] = doubleArrayOf(minMaxValues["min_return_on_equity"]!!.toDouble(), minMaxValues["max_return_on_equity"]!!.toDouble())
        minMaxValuesMap["quarterly_revenue_growth"] = doubleArrayOf(minMaxValues["min_quarterly_revenue_growth"]!!.toDouble(), minMaxValues["max_quarterly_revenue_growth"]!!.toDouble())
        minMaxValuesMap["quarterly_earnings_growth"] = doubleArrayOf(minMaxValues["min_quarterly_earnings_growth"]!!.toDouble(), minMaxValues["max_quarterly_earnings_growth"]!!.toDouble())
        minMaxValuesMap["total_debt_equity"] = doubleArrayOf(minMaxValues["min_total_debt_equity"]!!.toDouble(), minMaxValues["max_total_debt_equity"]!!.toDouble())
        minMaxValuesMap["forward_annual_dividend_yield"] = doubleArrayOf(minMaxValues["min_forward_annual_dividend_yield"]!!.toDouble(), minMaxValues["max_forward_annual_dividend_yield"]!!.toDouble())
        minMaxValuesMap["trailing_annual_dividend_yield"] = doubleArrayOf(minMaxValues["min_trailing_annual_dividend_yield"]!!.toDouble(), minMaxValues["max_trailing_annual_dividend_yield"]!!.toDouble())
        minMaxValuesMap["payout_ratio"] = doubleArrayOf(minMaxValues["min_payout_ratio"]!!.toDouble(), minMaxValues["max_payout_ratio"]!!.toDouble())
        minMaxValuesMap["interest_coverage_ratio"] = doubleArrayOf(minMaxValues["min_interest_coverage_ratio"]!!.toDouble(), minMaxValues["max_interest_coverage_ratio"]!!.toDouble())
        minMaxValuesMap["operating_cash_flow_net_income_ratio"] = doubleArrayOf(minMaxValues["min_operating_cash_flow_net_income_ratio"]!!.toDouble(), minMaxValues["max_operating_cash_flow_net_income_ratio"]!!.toDouble())
        minMaxValuesMap["free_cash_flow_conversion"] = doubleArrayOf(minMaxValues["min_free_cash_flow_conversion"]!!.toDouble(), minMaxValues["max_free_cash_flow_conversion"]!!.toDouble())
        minMaxValuesMap["debt_coverage_ratio"] = doubleArrayOf(minMaxValues["min_debt_coverage_ratio"]!!.toDouble(), minMaxValues["max_debt_coverage_ratio"]!!.toDouble())

        return minMaxValuesMap
    }
}