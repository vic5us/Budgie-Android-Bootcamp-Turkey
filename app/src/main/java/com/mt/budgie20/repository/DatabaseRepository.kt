package com.mt.budgie20.repository

import androidx.lifecycle.LiveData
import com.mt.budgie20.database.ExpenseDao
import com.mt.budgie20.database.RateDao
import com.mt.budgie20.database.UserDao
import com.mt.budgie20.model.Expense
import com.mt.budgie20.model.RateDb
import com.mt.budgie20.model.User

class DatabaseRepository(
    private val userDao: UserDao,
    private val expenseDao: ExpenseDao,
    private val rateDao: RateDao
) {

    /* User Operations*/

    suspend fun insertUser(user: User) = userDao.insert(user)

    val readAllUser: LiveData<List<User>> = userDao.getAllUser()

    suspend fun findUserById(userId: Int) = userDao.getUserById(userId)

    /*Rate Operations*/

    suspend fun insertRate(rateDb: RateDb) = rateDao.insert(rateDb)

    val readAllRates = rateDao.getAllRates()

    suspend fun findRateByLabel(rateLabel : String) = rateDao.getRateByLabel(rateLabel)

    /*ExpenseOperations*/

    suspend fun insertExpense(expense: Expense) = expenseDao.insert(expense)

    suspend fun deleteExpense(expense: Expense) = expenseDao.delete(expense)

    val readAllExpenses: LiveData<List<Expense>> = expenseDao.getAll()

    fun findExpenseById(expenseId : Int) = expenseDao.getByExpenseId(expenseId)

    val sumOfExpenses : LiveData<Long> = expenseDao.sumOfExpenses()

    fun sumOfExpenseByType(currency : String) = expenseDao.sumOfExpensesByType(currency)


}