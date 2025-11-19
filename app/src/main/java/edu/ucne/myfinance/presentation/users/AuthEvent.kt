package edu.ucne.myfinance.presentation.users

sealed interface AuthEvent {
    data class Submit(
        val email: String,
        val password: String,
        val username: String,
        val isRegister: Boolean
    ) : AuthEvent
    object Logout : AuthEvent
    data class NavigateToDashboard(val success: Boolean) : AuthEvent
    object NavigateToWelcome : AuthEvent
}