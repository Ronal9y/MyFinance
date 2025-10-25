package edu.ucne.myfinance.presentation.dashboard

sealed class DashboardEvent {
    object Refresh : DashboardEvent()
}