package edu.ucne.myfinance.domain.usecases.Dashboard

import  edu.ucne.myfinance.domain.usecases.Analytics.*
import  edu.ucne.myfinance.domain.usecases.Budgets.GetBudgetsCountUseCase
import  edu.ucne.myfinance.domain.usecases.Debts.GetDebtsCountUseCase
import  edu.ucne.myfinance.domain.usecases.Debts.GetTotalRemainingDebtUseCase
import  edu.ucne.myfinance.domain.usecases.Goals.GetGoalsCountUseCase
import  edu.ucne.myfinance.domain.usecases.Goals.GetTotalCompletionPercentageUseCase
import  edu.ucne.myfinance.domain.usecases.transaction.GetTransactionsUseCase
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import javax.inject.Inject

class GetDashboardDataUseCase @Inject constructor(
    private val getTransactions: GetTransactionsUseCase,
    private val getTotalIncome: GetTotalIncomeUseCase,
    private val getTotalExpenses: GetTotalExpensesUseCase,
    private val getBalance: GetBalanceUseCase,
    private val getGoalsCount: GetGoalsCountUseCase,
    private val getTotalCompletionPercentage: GetTotalCompletionPercentageUseCase,
    private val getDebtsCount: GetDebtsCountUseCase,
    private val getTotalRemainingDebt: GetTotalRemainingDebtUseCase,
    private val getBudgetsCount: GetBudgetsCountUseCase
) {
    suspend operator fun invoke(): DashboardData = coroutineScope {
        val transactionsAsync = async { getTransactions() }
        val totalIncomeAsync = async { getTotalIncome() }
        val totalExpensesAsync = async { getTotalExpenses() }
        val balanceAsync = async { getBalance() }
        val goalsCountAsync = async { getGoalsCount() }
        val completionPercentageAsync = async { getTotalCompletionPercentage() }
        val debtsCountAsync = async { getDebtsCount() }
        val totalRemainingDebtAsync = async { getTotalRemainingDebt() }
        val budgetsCountAsync = async { getBudgetsCount() }

        DashboardData(
            transactions = transactionsAsync.await(),
            totalIncome = totalIncomeAsync.await(),
            totalExpenses = totalExpensesAsync.await(),
            balance = balanceAsync.await(),
            goalsCount = goalsCountAsync.await(),
            completionPercentage = completionPercentageAsync.await(),
            debtsCount = debtsCountAsync.await(),
            totalRemainingDebt = totalRemainingDebtAsync.await(),
            budgetsCount = budgetsCountAsync.await()
        )
    }
}

data class DashboardData(
    val transactions: kotlinx.coroutines.flow.Flow<List< edu.ucne.myfinance.domain.model.Transaction>>,
    val totalIncome: Double,
    val totalExpenses: Double,
    val balance: Double,
    val goalsCount: Int,
    val completionPercentage: Double,
    val debtsCount: Int,
    val totalRemainingDebt: Double,
    val budgetsCount: Int
)