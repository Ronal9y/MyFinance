package edu.ucne.myfinance.data.repository

import edu.ucne.myfinance.data.local.dao.UserDao
import edu.ucne.myfinance.data.mapper.toEntity
import edu.ucne.myfinance.data.mapper.toExternalModel
import edu.ucne.myfinance.domain.model.User
import edu.ucne.myfinance.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import javax.inject.Inject

 class UserRepositoryImpl @Inject constructor(
    private val dao: UserDao
) : UserRepository {

    override fun observeCurrent(): Flow<User?> =
        dao.observeCurrent().map { it?.toExternalModel() }

    override fun observeIsLoggedIn(): Flow<Boolean> =
        dao.observeCount().map { it > 0 }

    override suspend fun getByEmail(email: String): User? =
        dao.observeByEmail(email).firstOrNull()?.toExternalModel()

    override suspend fun register(user: User): Result<Unit> =
        runCatching { dao.upsert(user.toEntity()) }

    override suspend fun clearSession(): Result<Unit> =
        runCatching { dao.clear() }
}