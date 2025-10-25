package edu.ucne.myfinance.domain.usecases.Goals

import edu.ucne.myfinance.domain.repository.GoalRepository
import javax.inject.Inject

class GetGoalsCountUseCase @Inject constructor(
    private val repository: GoalRepository
) {
    suspend operator fun invoke(): Int = repository.getGoalsCount()
}