package edu.ucne.myfinance.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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
import edu.ucne.myfinance.presentation.users.AuthViewModel
import edu.ucne.myfinance.presentation.users.LoginScreen
import edu.ucne.myfinance.presentation.users.SplashScreen
import edu.ucne.myfinance.presentation.users.WelcomeScreen

@Composable
fun FinanceNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    authViewModel: AuthViewModel
) {
    val uiState by authViewModel.uiState.collectAsState()
    val isLogged = uiState.isLoggedIn

    LaunchedEffect(isLogged) {
        if (isLogged && navController.currentBackStackEntry?.destination?.route == Screen.Login::class.qualifiedName) {
            navController.navigate(Screen.Dashboard) { popUpTo(0) { inclusive = true } }
        }
    }
    NavHost(
        navController = navController,
        startDestination = Screen.Splash,
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
                onBack = { navController.popBackStack() },
                onLogoutClick = {
                    navController.navigate(Screen.Welcome) {
                        popUpTo(0) { inclusive = true }
                    }
                },
                authViewModel = authViewModel
            )
        }

        composable<Screen.Welcome> {
            WelcomeScreen(
                onStartWithoutAccount = { navController.navigate(Screen.Dashboard) },
                onGoToLogin = { navController.navigate(Screen.Login) }
            )
        }
        composable<Screen.Login> {
            LoginScreen(onLoginSuccess = {
                navController.navigate(Screen.Dashboard) {
                    popUpTo(0) { inclusive = true }
                }
            })
        }
        composable<Screen.Splash> {
            SplashScreen(onTimeout = {
                navController.navigate(if (isLogged) Screen.Dashboard else Screen.Welcome) {
                    popUpTo(0) { inclusive = true }
                }
            })
        }

    }
}