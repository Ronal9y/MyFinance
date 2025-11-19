package edu.ucne.myfinance.domain.repository

import edu.ucne.myfinance.domain.model.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    fun observeCurrent(): Flow<User?>
    fun observeIsLoggedIn(): Flow<Boolean>
    suspend fun getByEmail(email: String): User?
    suspend fun register(user: User): Result<Unit>
    suspend fun clearSession(): Result<Unit>
}