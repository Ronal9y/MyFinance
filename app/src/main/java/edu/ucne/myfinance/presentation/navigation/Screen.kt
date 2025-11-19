package edu.ucne.myfinance.presentation.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed class Screen {
    @Serializable
    data object Dashboard : Screen()

    @Serializable
    data object Transactions : Screen()

    @Serializable
    data object Budgets : Screen()

    @Serializable
    data object AddBudget : Screen()

    @Serializable
    data object Goals : Screen()

    @Serializable
    data object AddGoal : Screen()

    @Serializable
    data object Debts : Screen()

    @Serializable
    data object AddDebt : Screen()
    @Serializable
    data object Analysis : Screen()

    @Serializable object Welcome : Screen()
    @Serializable object Login    : Screen()
    @Serializable object Splash   : Screen()
}