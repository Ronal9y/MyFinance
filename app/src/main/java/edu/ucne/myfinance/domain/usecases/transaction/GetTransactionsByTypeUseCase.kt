package edu.ucne.myfinance.domain.usecases.transaction

import edu.ucne.myfinance.domain.model.Transaction
import edu.ucne.myfinance.domain.repository.TransactionRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetTransactionsByTypeUseCase @Inject constructor(
    private val repository: TransactionRepository
) {
    operator fun invoke(type: String): Flow<List<Transaction>> = repository.getTransactionsByType(type)
}