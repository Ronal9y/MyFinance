package edu.ucne.myfinance.data.local.dao

import androidx.room.*
import edu.ucne.myfinance.data.local.entity.TransactionEntity
import edu.ucne.myfinance.domain.model.TransactionType
import kotlinx.coroutines.flow.Flow

@Dao
interface TransactionDao {
    @Query("SELECT * FROM transactions ORDER BY date DESC")
    fun observeAll(): Flow<List<TransactionEntity>>

    @Query("SELECT * FROM transactions WHERE type = :type ORDER BY date DESC")
    fun observeByType(type: String): Flow<List<TransactionEntity>>

    @Query("SELECT * FROM transactions WHERE id = :id LIMIT 1")
    suspend fun getById(id: Int): TransactionEntity?

    @Upsert
    suspend fun upsert(transaction: TransactionEntity)

    @Delete
    suspend fun delete(transaction: TransactionEntity)

    @Query("DELETE FROM transactions WHERE id = :id")
    suspend fun deleteById(id: Int)

    @Query("SELECT SUM(amount) FROM transactions WHERE type = :type")
    suspend fun getTotalByType(type: TransactionType): Double?

    @Query("SELECT COUNT(*) FROM transactions")
    suspend fun getCount(): Int

    @Query("SELECT COUNT(*) FROM transactions WHERE type = :type")
    suspend fun getCountByType(type: TransactionType): Int

    @Query("SELECT SUM(amount) FROM transactions WHERE type = 'EXPENSE' AND category = :category")
    suspend fun getExpensesByCategory(category: String): Double?
}