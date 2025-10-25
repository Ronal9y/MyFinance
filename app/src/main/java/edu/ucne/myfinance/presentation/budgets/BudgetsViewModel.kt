package edu.ucne.myfinance.presentation.budgets

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.ucne.myfinance.domain.model.Budget
import edu.ucne.myfinance.domain.usecases.Budgets.*
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BudgetsViewModel @Inject constructor(
    private val getBudgets: GetBudgetsUseCase,
    private val deleteBudgetById: DeleteBudgetByIdUseCase,
    private val insertBudget: InsertBudgetUseCase,
    private val updateBudgetSpent: UpdateBudgetSpentUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(BudgetsState())
    val state: StateFlow<BudgetsState> = _state.asStateFlow()

    init {
        loadBudgets()
        syncSpent() // 👈 recálculo inicial
    }

    fun onEvent(event: BudgetsEvent) {
        when (event) {
            BudgetsEvent.Refresh -> {
                syncSpent() // ← Sincronizar antes de cargar
                loadBudgets()
            }
            is BudgetsEvent.DeleteBudget -> deleteBudget(event.id)
            is BudgetsEvent.AddBudget -> addBudget(event)
        }
    }

    private fun loadBudgets() = viewModelScope.launch {
        getBudgets().collect { budgets ->
            _state.update { it.copy(budgets = budgets, isLoading = false, error = null) }
        }
    }

    private fun syncSpent() = viewModelScope.launch {
        runCatching {
            updateBudgetSpent() // ← Esto actualiza los spent en la base de datos
        }
            .onFailure { _state.update { s -> s.copy(error = it.message) } }
    }

    private fun deleteBudget(id: Int) = viewModelScope.launch {
        runCatching { deleteBudgetById(id) }
            .onFailure { _state.update { s -> s.copy(error = it.message) } }
    }

    private fun addBudget(event: BudgetsEvent.AddBudget) = viewModelScope.launch {
        val newBudget = Budget(
            id = 0,
            category = event.category,
            limit = event.limit,
            spent = 0.0,
            month = "Mes actual",
            alertThreshold = event.alertThreshold
        )
        runCatching { insertBudget(newBudget) }
            .onFailure { _state.update { s -> s.copy(error = it.message) } }
    }
}