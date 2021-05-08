package com.mt.budgie20.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.mt.budgie20.model.Expense
import com.mt.budgie20.model.User

@Dao
interface ExpenseDao {

    @Query("Select * from expense")
    fun getAll(): LiveData<List<Expense>>

    @Query("Select sum(amount) from expense")
    fun sumOfExpenses() : LiveData<Long>

    @Query("select * from expense where expenseId = :expenseId")
    fun getByExpenseId(expenseId :Int) : LiveData<Expense>

    @Query("Select * from expense where currency =  :currency")
    suspend fun findByType(currency: String): Expense

    @Query("SELECT sum(amount) FROM expense WHERE currency = :currency")
    fun sumOfExpensesByType(currency: String) : LiveData<Long>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(expense: Expense)

    @Delete
    suspend fun delete(expense: Expense)
}