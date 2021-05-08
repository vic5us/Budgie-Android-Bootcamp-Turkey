package com.mt.budgie20.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "expense")
data class Expense(
    @PrimaryKey(autoGenerate = true)
    val expenseId: Int,
    @ColumnInfo(name="type")
    val type : String,
    @ColumnInfo(name = "description")
    val description: String,
    @ColumnInfo(name = "amount")
    var amount: Long,
    @ColumnInfo(name = "currency")
    val currency: String
)