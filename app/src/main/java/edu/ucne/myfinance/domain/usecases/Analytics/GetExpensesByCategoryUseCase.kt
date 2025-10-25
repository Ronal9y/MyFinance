package edu.ucne.myfinance.domain.usecases.Analytics

import edu.ucne.myfinance.domain.repository.TransactionRepository
import javax.inject.Inject

class GetExpensesByCategoryUseCase @Inject constructor(
    private val repository: TransactionRepository
) {
    suspend operator fun invoke(category: String): Double = repository.getExpensesByCategory(category)
}