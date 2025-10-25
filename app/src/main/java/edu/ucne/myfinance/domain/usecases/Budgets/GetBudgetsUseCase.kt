package edu.ucne.myfinance.domain.usecases.Budgets

import edu.ucne.myfinance.domain.model.Budget
import edu.ucne.myfinance.domain.repository.BudgetRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetBudgetsUseCase @Inject constructor(
    private val repository: BudgetRepository
) {
    operator fun invoke(): Flow<List<Budget>> = repository.getAllBudgets()
}