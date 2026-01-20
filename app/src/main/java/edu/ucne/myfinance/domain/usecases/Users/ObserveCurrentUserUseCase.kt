package edu.ucne.myfinance.domain.usecases.Users

import edu.ucne.myfinance.domain.model.User
import edu.ucne.myfinance.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ObserveCurrentUserUseCase @Inject constructor(
    private val repo: UserRepository
) {
    operator fun invoke(): Flow<User?> = repo.observeCurrent()
}