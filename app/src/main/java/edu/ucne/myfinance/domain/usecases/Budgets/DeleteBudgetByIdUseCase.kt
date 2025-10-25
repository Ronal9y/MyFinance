package edu.ucne.myfinance.domain.usecases.Budgets

import edu.ucne.myfinance.domain.repository.BudgetRepository
import javax.inject.Inject

class DeleteBudgetByIdUseCase @Inject constructor(
    private val repository: BudgetRepository
) {
    suspend operator fun invoke(id: Int) = repository.deleteBudgetById(id)
}