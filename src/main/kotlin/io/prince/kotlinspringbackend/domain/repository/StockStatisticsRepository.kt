package io.prince.kotlinspringbackend.domain.repository

import io.prince.kotlinspringbackend.domain.model.StockStatistics
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface StockStatisticsRepository: JpaRepository<StockStatistics, String>, JpaSpecificationExecutor<StockStatistics> {
    fun findByTicker(ticker: String): Optional<StockStatistics>

    @Query(value = "SELECT \n" +
            "    MIN(trailing_pe) AS min_trailing_pe, \n" +
            "    MAX(trailing_pe) AS max_trailing_pe, \n" +
            "    MIN(price_sales) AS min_price_sales, \n" +
            "    MAX(price_sales) AS max_price_sales, \n" +
            "    MIN(price_book) AS min_price_book, \n" +
            "    MAX(price_book) AS max_price_book, \n" +
            "    MIN(enterprise_value_editda) AS min_enterprise_value_ebitda, \n" +
            "    MAX(enterprise_value_editda) AS max_enterprise_value_ebitda, \n" +
            "    MIN(return_on_equity) AS min_return_on_equity, \n" +
            "    MAX(return_on_equity) AS max_return_on_equity, \n" +
            "    MIN(quarterly_revenue_growth) AS min_quarterly_revenue_growth, \n" +
            "    MAX(quarterly_revenue_growth) AS max_quarterly_revenue_growth, \n" +
            "    MIN(quarterly_earnings_growth) AS min_quarterly_earnings_growth, \n" +
            "    MAX(quarterly_earnings_growth) AS max_quarterly_earnings_growth, \n" +
            "    MIN(total_debt_equity) AS min_total_debt_equity, \n" +
            "    MAX(total_debt_equity) AS max_total_debt_equity, \n" +
            "    MIN(forward_annual_dividend_yield) AS min_forward_annual_dividend_yield, \n" +
            "    MAX(forward_annual_dividend_yield) AS max_forward_annual_dividend_yield, \n" +
            "    MIN(trailing_annual_dividend_yield) AS min_trailing_annual_dividend_yield, \n" +
            "    MAX(trailing_annual_dividend_yield) AS max_trailing_annual_dividend_yield, \n" +
            "    MIN(payout_ratio) AS min_payout_ratio, \n" +
            "    MAX(payout_ratio) AS max_payout_ratio, \n" +
            "    MIN(interest_coverage_ratio) AS min_interest_coverage_ratio, \n" +
            "    MAX(interest_coverage_ratio) AS max_interest_coverage_ratio, \n" +
            "    MIN(operating_cash_flow_net_income_ratio) AS min_operating_cash_flow_net_income_ratio, \n" +
            "    MAX(operating_cash_flow_net_income_ratio) AS max_operating_cash_flow_net_income_ratio, \n" +
            "    MIN(free_cash_flow_conversion) AS min_free_cash_flow_conversion, \n" +
            "    MAX(free_cash_flow_conversion) AS max_free_cash_flow_conversion, \n" +
            "    MIN(debt_coverage_ratio) AS min_debt_coverage_ratio, \n" +
            "    MAX(debt_coverage_ratio) AS max_debt_coverage_ratio \n" +
            "FROM tbl_stock_statistics;", nativeQuery = true)
    fun findMinMaxValues(): Map<String, Float>
}