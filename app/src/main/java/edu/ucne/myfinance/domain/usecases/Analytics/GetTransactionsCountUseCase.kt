package edu.ucne.myfinance.domain.usecases.Analytics

import edu.ucne.myfinance.domain.repository.TransactionRepository
import javax.inject.Inject

class GetTransactionsCountUseCase @Inject constructor(
    private val repository: TransactionRepository
) {
    suspend operator fun invoke(): Int = repository.getTransactionsCount()
}