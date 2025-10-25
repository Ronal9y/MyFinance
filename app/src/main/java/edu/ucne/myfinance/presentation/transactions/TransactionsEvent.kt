package edu.ucne.myfinance.presentation.transactions

import edu.ucne.myfinance.domain.model.Transaction

sealed interface TransactionsEvent {
    data class FilterChanged(val filter: String) : TransactionsEvent
    data class DeleteTransaction(val id: Int) : TransactionsEvent
    data class AddTransaction(val transaction: Transaction) : TransactionsEvent
    object Refresh : TransactionsEvent
}