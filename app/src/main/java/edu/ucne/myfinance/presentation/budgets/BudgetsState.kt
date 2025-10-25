package edu.ucne.myfinance.presentation.budgets

import edu.ucne.myfinance.domain.model.Budget

data class BudgetsState(
    val budgets: List<Budget> = emptyList(),
    val isLoading: Boolean = true,
    val error: String? = null
)

