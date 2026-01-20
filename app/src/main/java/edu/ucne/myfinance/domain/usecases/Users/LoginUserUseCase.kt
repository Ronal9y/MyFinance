package edu.ucne.myfinance.domain.usecases.Users

import edu.ucne.myfinance.domain.repository.UserRepository
import javax.inject.Inject

class LoginUserUseCase @Inject constructor(
    private val repo: UserRepository
) {
    suspend operator fun invoke(email: String): Boolean =
        repo.getByEmail(email) != null
}