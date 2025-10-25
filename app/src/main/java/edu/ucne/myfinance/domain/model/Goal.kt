package edu.ucne.myfinance.domain.model

data class Goal(
    val id: Int = 0,
    val name: String,
    val targetAmount: Double,
    val currentAmount: Double = 0.0,
    val deadline: String,
    val description: String
)
