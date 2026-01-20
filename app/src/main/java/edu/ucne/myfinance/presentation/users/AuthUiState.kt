package edu.ucne.myfinance.presentation.users

data class AuthUiState(
    val isLoggedIn: Boolean = false,
    val isLoading: Boolean = false,
    val error: String? = null
)
