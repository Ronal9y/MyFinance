package edu.ucne.myfinance.domain.usecases.transaction

import edu.ucne.myfinance.domain.model.Transaction
import edu.ucne.myfinance.domain.repository.TransactionRepository
import javax.inject.Inject

class GetTransactionByIdUseCase @Inject constructor(
    private val repository: TransactionRepository
) {
    suspend operator fun invoke(id: Int): Transaction? = repository.getTransactionById(id)
}