package io.prince.kotlinspringbackend.impl.domain.service

import io.prince.kotlinspringbackend.domain.model.StockStatistics
import io.prince.kotlinspringbackend.domain.repository.StockStatisticsRepository
import io.prince.kotlinspringbackend.domain.service.StockStatisticsService
import jakarta.persistence.criteria.CriteriaBuilder
import jakarta.persistence.criteria.Predicate
import jakarta.persistence.criteria.Root
import org.springframework.data.jpa.domain.Specification
import org.springframework.stereotype.Service

@Service
class StockStatisticsServiceImpl(
    private val stockStatisticsRepository: StockStatisticsRepository
): StockStatisticsService {
    override fun buildPredicate(
        root: Root<StockStatistics>,
        criteriaBuilder: CriteriaBuilder,
        filterName: String,
        filterValues: List<Double>
    ): Predicate {
        val min = filterValues[0]
        val max = filterValues[1]
        return criteriaBuilder.between(root.get(filterName), min, max)
    }

    override fun buildSpecifications(filters: Map<String, List<Double>>): List<Specification<StockStatistics>> {
        val specifications = mutableListOf<Specification<StockStatistics>>()
        filters.forEach { (filterName, filterValues) ->
            val spec = Specification { root, _, criteriaBuilder ->
                buildPredicate(root, criteriaBuilder, filterName, filterValues)
            }
            specifications.add(spec)
        }
        return specifications
    }

    override fun findStocksWithDynamicFilters(filters: Map<String, List<Double>>): List<StockStatistics> {
        val specifications = buildSpecifications(filters)
        val combinedSpec = Specification.where(specifications[0])
        for (i in 1 until specifications.size) {
            combinedSpec.and(specifications[i])
        }
        return stockStatisticsRepository.findAll(combinedSpec)
    }
}