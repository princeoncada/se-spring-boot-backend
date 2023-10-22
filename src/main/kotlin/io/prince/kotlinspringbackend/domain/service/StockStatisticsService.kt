package io.prince.kotlinspringbackend.domain.service

import io.prince.kotlinspringbackend.domain.model.StockStatistics
import jakarta.persistence.criteria.CriteriaBuilder
import jakarta.persistence.criteria.Predicate
import jakarta.persistence.criteria.Root
import org.springframework.data.jpa.domain.Specification
import org.springframework.stereotype.Service

@Service
interface StockStatisticsService {
    fun buildPredicate(
        root: Root<StockStatistics>,
        criteriaBuilder: CriteriaBuilder,
        filterName: String,
        filterValues: List<Double>
    ): Predicate
    fun buildSpecifications(filters: Map<String, List<Double>>): List<Specification<StockStatistics>>
    fun findStocksWithDynamicFilters(filters: Map<String, List<Double>>): List<StockStatistics>
}