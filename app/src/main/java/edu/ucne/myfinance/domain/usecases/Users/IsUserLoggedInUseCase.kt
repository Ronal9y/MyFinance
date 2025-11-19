package edu.ucne.myfinance.domain.usecases.Users

import edu.ucne.myfinance.domain.repository.UserRepository
import javax.inject.Inject

class IsUserLoggedInUseCase @Inject constructor(
    private val repo: UserRepository
) {
    operator fun invoke() = repo.observeIsLoggedIn()   // ⬅ Flow<Boolean>
}