package edu.ucne.myfinance.presentation.transactions

import edu.ucne.myfinance.domain.model.Transaction

data class TransactionsState(
    val transactions: List<Transaction> = emptyList(),
    val selectedFilter: String = "Todas",
    val isLoading: Boolean = true,
    val error: String? = null
)
