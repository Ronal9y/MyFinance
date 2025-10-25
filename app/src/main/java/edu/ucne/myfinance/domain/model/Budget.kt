package edu.ucne.myfinance.domain.model

data class Budget(
    val id: Int = 0,
    val category: CategoryType,
    val limit: Double,
    val spent: Double = 0.0,
    val month: String,
    val alertThreshold: Int = 80
)