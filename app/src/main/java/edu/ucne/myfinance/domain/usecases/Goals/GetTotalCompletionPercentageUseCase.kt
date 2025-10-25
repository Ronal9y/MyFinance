package edu.ucne.myfinance.domain.usecases.Goals

import edu.ucne.myfinance.domain.repository.GoalRepository
import javax.inject.Inject

class GetTotalCompletionPercentageUseCase @Inject constructor(
    private val repository: GoalRepository
) {
    suspend operator fun invoke(): Double = repository.getTotalCompletionPercentage()
}