package io.prince.kotlinspringbackend.domain.model

import jakarta.persistence.*

@Entity
@Table(name = "tbl_user_stocks")
data class UserStock(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    val id: String,

    @Column(name = "ticker", nullable = false)
    val ticker: String,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    val user: User,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "stock_id", referencedColumnName = "id")
    val stock: Stock
)