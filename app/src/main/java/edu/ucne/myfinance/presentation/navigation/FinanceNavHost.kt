package edu.ucne.myfinance.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import edu.ucne.myfinance.presentation.analysis.AnalysisScreen
import edu.ucne.myfinance.presentation.budgets.BudgetsScreen
import edu.ucne.myfinance.presentation.dashboard.DashboardScreen
import edu.ucne.myfinance.presentation.debts.DeudaListScreen
import edu.ucne.myfinance.presentation.goals.GoalsScreen
import edu.ucne.myfinance.presentation.transactions.TransactionsScreen

@Composable
fun FinanceNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Dashboard,
        modifier = modifier
    ) {
        composable<Screen.Dashboard> {
            DashboardScreen(
                onAddTransaction = {  },
                onViewTransactions = { navController.navigate(Screen.Transactions) },
                onViewGoals = { navController.navigate(Screen.Goals) },
                onViewDebts = { navController.navigate(Screen.Analysis) }
            )
        }

        composable<Screen.Transactions> {
            TransactionsScreen(
                onBack = { navController.popBackStack() },
                onAddTransaction = {  }
            )
        }

        composable<Screen.Budgets> {
            BudgetsScreen(
                onBack = { navController.popBackStack() }
            )
        }

        composable<Screen.Goals> {
            GoalsScreen(
                onBack = { navController.popBackStack() }
            )
        }
        composable<Screen.Debts> {
            DeudaListScreen(
                onBack = { navController.popBackStack() }

            )
        }
        composable<Screen.Analysis> {
            AnalysisScreen(
                onBack = { navController.popBackStack() }
            )
        }
    }
}