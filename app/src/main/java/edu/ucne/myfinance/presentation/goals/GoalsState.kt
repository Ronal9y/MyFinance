package edu.ucne.myfinance.presentation.goals

import edu.ucne.myfinance.domain.model.Goal

data class GoalsState(
    val goals: List<Goal> = emptyList(),
    val isLoading: Boolean = true,
    val error: String? = null
)
