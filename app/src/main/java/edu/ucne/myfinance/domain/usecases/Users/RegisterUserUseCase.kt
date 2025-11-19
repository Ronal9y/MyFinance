package edu.ucne.myfinance.domain.usecases.Users

import edu.ucne.myfinance.domain.model.User
import edu.ucne.myfinance.domain.repository.UserRepository
import javax.inject.Inject

class RegisterUserUseCase @Inject constructor(
    private val repo: UserRepository
) {
    suspend operator fun invoke(user: User) = repo.register(user)
}