package edu.ucne.myfinance.data.mapper

import edu.ucne.myfinance.data.local.entity.BudgetEntity
import edu.ucne.myfinance.domain.model.Budget

fun BudgetEntity.asExternalModel(): Budget = Budget(
    id = id,
    category = enumValueOf(category),
    limit = limit,
    spent = spent,
    month = month,
    alertThreshold = alertThreshold
)

fun Budget.toEntity(): BudgetEntity = BudgetEntity(
    id = id,
    category = category.name,
    limit = limit,
    spent = spent,
    month = month,
    alertThreshold = alertThreshold
)