package edu.ucne.myfinance.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import  edu.ucne.myfinance.domain.model.CategoryType
import  edu.ucne.myfinance.domain.model.TransactionType

@Entity(tableName = "transactions")
data class TransactionEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val type: TransactionType,
    val amount: Double,
    val category: CategoryType,
    val description: String,
    val date: String
)
