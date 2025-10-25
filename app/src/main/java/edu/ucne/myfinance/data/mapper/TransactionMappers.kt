package edu.ucne.myfinance.data.mapper

import edu.ucne.myfinance.data.local.entity.TransactionEntity
import edu.ucne.myfinance.domain.model.Transaction

fun TransactionEntity.asExternalModel(): Transaction = Transaction(
    id = id,
    type = type,
    amount = amount,
    category = category,
    description = description,
    date = date
)

fun Transaction.toEntity(): TransactionEntity = TransactionEntity(
    id = id,
    type = type,
    amount = amount,
    category = category,
    description = description,
    date = date
)
