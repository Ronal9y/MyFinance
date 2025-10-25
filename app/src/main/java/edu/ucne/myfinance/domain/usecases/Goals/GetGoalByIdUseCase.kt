package edu.ucne.myfinance.domain.usecases.Goals

import edu.ucne.myfinance.domain.model.Goal
import edu.ucne.myfinance.domain.repository.GoalRepository
import javax.inject.Inject

class GetGoalByIdUseCase @Inject constructor(
    private val repository: GoalRepository
) {
    suspend operator fun invoke(id: Int): Goal? = repository.getGoalById(id)
}