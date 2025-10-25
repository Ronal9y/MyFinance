package edu.ucne.myfinance.domain.usecases.Budgets

import edu.ucne.myfinance.domain.model.TransactionType
import edu.ucne.myfinance.domain.repository.BudgetRepository
import edu.ucne.myfinance.domain.repository.TransactionRepository
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class UpdateBudgetSpentUseCase @Inject constructor(
    private val budgetRepo: BudgetRepository,
    private val transactionRepo: TransactionRepository
) {
    suspend operator fun invoke() {
        // 1. Obtener todos los presupuestos activos
        val budgets = budgetRepo.getAllBudgets().first()

        // 2. Obtener todos los gastos
        val expenses = transactionRepo.getAllTransactions()
            .first()
            .filter { it.type == TransactionType.EXPENSE }

        // 3. Acumular por categoría - CORREGIDO: usar el enum directamente
        val spentByCategory = expenses.groupBy { it.category }
            .mapValues { (_, list) -> list.sumOf { it.amount } }

        // 4. Actualizar cada presupuesto - CORREGIDO: comparación directa de enums
        budgets.forEach { budget ->
            val spent = spentByCategory[budget.category] ?: 0.0
            val updated = budget.copy(spent = spent)
            budgetRepo.updateBudget(updated)
        }
    }
}