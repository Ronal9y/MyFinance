package edu.ucne.myfinance.domain.repository

import edu.ucne.myfinance.domain.model.Budget
import kotlinx.coroutines.flow.Flow

interface BudgetRepository {
    fun getAllBudgets(): Flow<List<Budget>>
    suspend fun getBudgetById(id: Int): Budget?
    suspend fun insertBudget(budget: Budget)
    suspend fun updateBudget(budget: Budget)
    suspend fun deleteBudget(budget: Budget)
    suspend fun deleteBudgetById(id: Int)
    suspend fun getBudgetsCount(): Int
}