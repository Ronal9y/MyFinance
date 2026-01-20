package edu.ucne.myfinance.presentation.users

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.ucne.myfinance.domain.model.User
import edu.ucne.myfinance.domain.usecases.Users.LoginUserUseCase
import edu.ucne.myfinance.domain.usecases.Users.LogoutUserUseCase
import edu.ucne.myfinance.domain.usecases.Users.ObserveCurrentUserUseCase
import edu.ucne.myfinance.domain.usecases.Users.ObserveIsLoggedInUseCase
import edu.ucne.myfinance.domain.usecases.Users.RegisterUserUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val register: RegisterUserUseCase,
    private val login: LoginUserUseCase,
    private val observeIsLoggedIn: ObserveIsLoggedInUseCase,
    private val logout: LogoutUserUseCase,
    private val observeCurrentUser: ObserveCurrentUserUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(AuthUiState())
    val uiState: StateFlow<AuthUiState> = _uiState.asStateFlow()

    private val _event = MutableSharedFlow<AuthEvent>()
    val event: SharedFlow<AuthEvent> = _event.asSharedFlow()

    val currentUser: Flow<User?> = observeCurrentUser()

    init {
        viewModelScope.launch {
            observeIsLoggedIn().collect { logged ->
                _uiState.update { it.copy(isLoggedIn = logged) }
            }
        }
    }

    fun onEvent(event: AuthEvent) {
        when (event) {
            is AuthEvent.Submit -> submit(event.email, event.password, event.username, event.isRegister)
            AuthEvent.Logout -> logout()
            else -> {}
        }
    }

    private fun submit(email: String, password: String, username: String, isRegister: Boolean) {
        viewModelScope.launch {
            if (isRegister) {
                register(User(email = email, password = password, username = username))
            }
            val success = login(email) != null
            _uiState.update { it.copy(isLoggedIn = success) } // ⬅ fuerza estado on
            _event.emit(AuthEvent.NavigateToDashboard(success))
        }
    }

    private fun logout() {
        viewModelScope.launch {
            logout() // ⬅ borra tabla
            _uiState.update { it.copy(isLoggedIn = false) } // ⬅ fuerza estado OFF
            _event.emit(AuthEvent.NavigateToWelcome)
        }
    }
}