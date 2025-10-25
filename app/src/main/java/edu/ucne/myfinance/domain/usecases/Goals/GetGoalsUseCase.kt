package edu.ucne.myfinance.domain.usecases.Goals

import edu.ucne.myfinance.domain.model.Goal
import edu.ucne.myfinance.domain.repository.GoalRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetGoalsUseCase @Inject constructor(
    private val repository: GoalRepository
) {
    operator fun invoke(): Flow<List<Goal>> = repository.getAllGoals()
}