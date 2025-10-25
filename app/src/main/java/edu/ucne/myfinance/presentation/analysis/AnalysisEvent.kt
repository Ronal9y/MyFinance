package edu.ucne.myfinance.presentation.analysis

sealed interface AnalysisEvent {
    object Refresh : AnalysisEvent
    data class AddInsight(val title: String, val description: String) : AnalysisEvent
}