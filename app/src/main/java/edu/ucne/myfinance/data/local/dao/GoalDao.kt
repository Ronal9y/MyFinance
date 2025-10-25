package edu.ucne.myfinance.data.local.dao

import androidx.room.*
import edu.ucne.myfinance.data.local.entity.GoalEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface GoalDao {
    @Query("SELECT * FROM goals ORDER BY deadline ASC")
    fun observeAll(): Flow<List<GoalEntity>>

    @Query("SELECT * FROM goals WHERE id = :id LIMIT 1")
    suspend fun getById(id: Int): GoalEntity?

    @Upsert
    suspend fun upsert(goal: GoalEntity)

    @Delete
    suspend fun delete(goal: GoalEntity)

    @Query("DELETE FROM goals WHERE id = :id")
    suspend fun deleteById(id: Int)

    @Query("SELECT COUNT(*) FROM goals")
    suspend fun getCount(): Int

    @Query("SELECT * FROM goals")
    suspend fun getAll(): List<GoalEntity>
}