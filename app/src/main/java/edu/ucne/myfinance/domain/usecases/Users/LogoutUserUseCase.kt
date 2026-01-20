package edu.ucne.myfinance.domain.usecases.Users

import edu.ucne.myfinance.domain.repository.UserRepository
import javax.inject.Inject

class LogoutUserUseCase @Inject constructor(
    private val repo: UserRepository
) {
    suspend operator fun invoke() = repo.clearSession()
}