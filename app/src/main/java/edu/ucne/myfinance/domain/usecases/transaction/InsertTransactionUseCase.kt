package edu.ucne.myfinance.domain.usecases.transaction

import edu.ucne.myfinance.domain.model.Transaction
import edu.ucne.myfinance.domain.repository.TransactionRepository
import javax.inject.Inject

class InsertTransactionUseCase @Inject constructor(
    private val repository: TransactionRepository
) {
    suspend operator fun invoke(transaction: Transaction) = repository.insertTransaction(transaction)
}