package edu.ucne.myfinance.data.local.dao

import androidx.room.*
import edu.ucne.myfinance.data.local.entity.UserEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    @Query("SELECT * FROM users WHERE userId = :id LIMIT 1")
    fun observeById(id: Int): Flow<UserEntity?>

    @Query("SELECT * FROM users WHERE email = :email LIMIT 1")
    fun observeByEmail(email: String): Flow<UserEntity?>

    @Query("SELECT * FROM users LIMIT 1")
    fun observeCurrent(): Flow<UserEntity?>

    @Upsert
    suspend fun upsert(user: UserEntity)

    @Query("DELETE FROM users")
    suspend fun clear()

    @Query("SELECT COUNT(*) FROM users")
    fun observeCount(): Flow<Int>
}