package edu.ucne.myfinance.domain.usecases.Debts

import edu.ucne.myfinance.domain.repository.DebtRepository
import javax.inject.Inject

class GetDebtsCountUseCase @Inject constructor(
    private val repository: DebtRepository
) {
    suspend operator fun invoke(): Int = repository.getDebtsCount()
}