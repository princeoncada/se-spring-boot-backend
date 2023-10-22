package io.prince.kotlinspringbackend.domain.model

import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.time.Instant

@Entity
@Table(name = "tbl_stock_statistics")
data class StockStatistics(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    val id: String,

    @Column(name = "ticker", nullable = false, unique = true)
    val ticker: String,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "stock_id", referencedColumnName = "id")
    val stock: Stock,

    @Column(name = "trailing_pe", nullable = false)
    val trailingPe: Double,

    @Column(name = "price_sales", nullable = false)
    val priceSales: Double,

    @Column(name = "price_book", nullable = false)
    val priceBook: Double,

    @Column(name = "enterprise_value_editda", nullable = false)
    val enterpriseValueEbitda: Double,

    @Column(name = "return_on_equity", nullable = false)
    val returnOnEquity: Double,

    @Column(name = "quarterly_revenue_growth", nullable = false)
    val quarterlyRevenueGrowth: Double,

    @Column(name = "quarterly_earnings_growth", nullable = false)
    val quarterlyEarningsGrowth: Double,

    @Column(name = "total_debt_equity", nullable = false)
    val totalDebtEquity: Double,

    @Column(name = "forward_annual_dividend_yield", nullable = false)
    val forwardAnnualDividendYield: Double,

    @Column(name = "trailing_annual_dividend_yield", nullable = false)
    val trailingAnnualDividendYield: Double,

    @Column(name = "payout_ratio", nullable = false)
    val payoutRatio: Double,

    @Column(name = "interest_coverage_ratio", nullable = false)
    val interestCoverageRatio: Double,

    @Column(name = "operating_cash_flow_net_income_ratio", nullable = false)
    val operatingCashFlowNetIncomeRatio: Double,

    @Column(name = "free_cash_flow_conversion", nullable = false)
    val freeCashFlowConversion: Double,

    @Column(name = "debt_coverage_ratio", nullable = false)
    val debtCoverageRatio: Double,

    @CreationTimestamp
    @Column(name = "created_at", nullable = false)
    val createdAt: Instant,

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    val updatedAt: Instant
)