package edu.ucne.myfinance.data.mapper

import edu.ucne.myfinance.data.local.entity.UserEntity
import edu.ucne.myfinance.domain.model.User

fun UserEntity.toExternalModel() = User(
    userId = userId,
    username = username,
    email = email,
    password = password
)

fun User.toEntity() = UserEntity(
    username = username,
    email = email,
    password = password
)