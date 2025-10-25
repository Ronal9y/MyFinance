package edu.ucne.myfinance.domain.usecases.Debts

import edu.ucne.myfinance.domain.repository.DebtRepository
import javax.inject.Inject

class GetTotalRemainingDebtUseCase @Inject constructor(
    private val repository: DebtRepository
) {
    suspend operator fun invoke(): Double = repository.getTotalRemainingDebt()
}