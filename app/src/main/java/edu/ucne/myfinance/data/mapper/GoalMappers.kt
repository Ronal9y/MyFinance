package edu.ucne.myfinance.data.mapper

import edu.ucne.myfinance.data.local.entity.GoalEntity
import edu.ucne.myfinance.domain.model.Goal

fun GoalEntity.asExternalModel(): Goal = Goal(
    id = id,
    name = name,
    targetAmount = targetAmount,
    currentAmount = currentAmount,
    deadline = deadline,
    description = description
)

fun Goal.toEntity(): GoalEntity = GoalEntity(
    id = id,
    name = name,
    targetAmount = targetAmount,
    currentAmount = currentAmount,
    deadline = deadline,
    description = description
)