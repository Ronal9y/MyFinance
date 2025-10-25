package edu.ucne.myfinance.domain.usecases.Budgets

import edu.ucne.myfinance.domain.model.Budget
import edu.ucne.myfinance.domain.repository.BudgetRepository
import javax.inject.Inject

class DeleteBudgetUseCase @Inject constructor(
    private val repository: BudgetRepository
) {
    suspend operator fun invoke(budget: Budget) = repository.deleteBudget(budget)
}