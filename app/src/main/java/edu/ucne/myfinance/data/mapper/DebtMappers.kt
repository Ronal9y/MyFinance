package edu.ucne.myfinance.data.mapper

import edu.ucne.myfinance.data.local.entity.DebtEntity
import edu.ucne.myfinance.domain.model.Debt

fun DebtEntity.asExternalModel(): Debt = Debt(
    id = id,
    name = name,
    principalAmount = principalAmount,
    interestRate = interestRate,
    interestType = enumValueOf(interestType),
    compoundingPeriod = enumValueOf(compoundingPeriod),
    dueDate = dueDate,
    remainingAmount = remainingAmount,
    creditor = creditor,
    status = enumValueOf(status),
    penaltyRate = penaltyRate,
    creationDate = creationDate
)

fun Debt.toEntity(): DebtEntity = DebtEntity(
    id = id,
    name = name,
    principalAmount = principalAmount,
    interestRate = interestRate,
    interestType = interestType.name,
    compoundingPeriod = compoundingPeriod.name,
    dueDate = dueDate,
    remainingAmount = remainingAmount,
    creditor = creditor,
    status = status.name,
    penaltyRate = penaltyRate,
    creationDate = creationDate
)