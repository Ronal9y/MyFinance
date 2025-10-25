package edu.ucne.myfinance.data.local.database

import edu.ucne.myfinance.data.local.dao.BudgetDao
import edu.ucne.myfinance.data.local.dao.DebtDao
import edu.ucne.myfinance.data.local.dao.GoalDao
import edu.ucne.myfinance.data.local.dao.TransactionDao
import edu.ucne.myfinance.data.local.entity.BudgetEntity
import edu.ucne.myfinance.data.local.entity.DebtEntity
import edu.ucne.myfinance.data.local.entity.GoalEntity
import edu.ucne.myfinance.data.local.entity.TransactionEntity
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters


@Database(
    entities = [
        TransactionEntity::class,
        BudgetEntity::class,
        GoalEntity::class,
        DebtEntity::class
    ],
    version = 7,
    exportSchema = false
)
abstract class FinanceDatabase : RoomDatabase() {
    abstract fun transactionDao(): TransactionDao
    abstract fun budgetDao(): BudgetDao
    abstract fun goalDao(): GoalDao
    abstract fun debtDao(): DebtDao
}