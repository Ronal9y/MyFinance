package edu.ucne.myfinance.domain.usecases.Users

import edu.ucne.myfinance.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ObserveIsLoggedInUseCase @Inject constructor(
    private val repo: UserRepository
) {
    operator fun invoke(): Flow<Boolean> = repo.observeIsLoggedIn()
}