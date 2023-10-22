package io.prince.kotlinspringbackend.domain.model

import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.time.Instant
import java.time.LocalDateTime

@Entity
@Table(name = "tbl_stocks")
data class Stock(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    val id: String,

    @Column(name = "ticker", nullable = false, unique = true)
    val ticker: String,

    @Column(name = "name", nullable = false, unique = true)
    val name: String,

    @Column(name = "price", nullable = false)
    val price: Double,

    @Column(name = "stock_data", nullable = false, columnDefinition = "MEDIUMTEXT")
    val stockData: String,

    @Column(name = "growth", nullable = false)
    val growth: Int,

    @Column(name = "dividend", nullable = false)
    val dividend: Int,

    @Column(name = "value", nullable = false)
    val value: Int,

    @Column(name = "total", nullable = false)
    val total: Int,

    @Column(name = "expiry", nullable = false)
    val expiry: LocalDateTime,

    @CreationTimestamp
    @Column(name = "created_at", nullable = false)
    val createdAt: Instant,

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    val updatedAt: Instant
)