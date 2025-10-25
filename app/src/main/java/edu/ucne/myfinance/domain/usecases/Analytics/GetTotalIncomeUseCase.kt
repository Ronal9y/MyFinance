package edu.ucne.myfinance.domain.usecases.Analytics

import edu.ucne.myfinance.domain.repository.TransactionRepository
import javax.inject.Inject

class GetTotalIncomeUseCase @Inject constructor(
    private val repository: TransactionRepository
) {
    suspend operator fun invoke(): Double = repository.getTotalIncome()
}