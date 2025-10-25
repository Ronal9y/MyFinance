package edu.ucne.myfinance.domain.usecases.transaction

import edu.ucne.myfinance.domain.repository.TransactionRepository
import javax.inject.Inject

class DeleteTransactionByIdUseCase @Inject constructor(
    private val repository: TransactionRepository
) {
    suspend operator fun invoke(id: Int) = repository.deleteTransactionById(id)
}