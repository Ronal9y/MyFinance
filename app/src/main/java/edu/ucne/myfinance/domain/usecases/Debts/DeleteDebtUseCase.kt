package edu.ucne.myfinance.domain.usecases.Debts

import edu.ucne.myfinance.domain.model.Debt
import edu.ucne.myfinance.domain.repository.DebtRepository
import javax.inject.Inject

class DeleteDebtUseCase @Inject constructor(
    private val repository: DebtRepository
) {
    suspend operator fun invoke(debt: Debt) = repository.deleteDebt(debt)
}