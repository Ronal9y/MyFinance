package edu.ucne.myfinance.domain.usecases.transaction

import edu.ucne.myfinance.domain.model.Transaction
import edu.ucne.myfinance.domain.repository.TransactionRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetTransactionsUseCase @Inject constructor(
    private val repository: TransactionRepository
) {
    operator fun invoke(): Flow<List<Transaction>> = repository.getAllTransactions()
}