package edu.ucne.myfinance.domain.usecases.Debts

import edu.ucne.myfinance.domain.model.Debt
import edu.ucne.myfinance.domain.repository.DebtRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetDebtsUseCase @Inject constructor(
    private val repository: DebtRepository
) {
    operator fun invoke(): Flow<List<Debt>> = repository.getAllDebts()
}