package com.mt.budgie20.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.mt.budgie20.database.ExpenseDatabase
import com.mt.budgie20.model.Expense
import com.mt.budgie20.model.Rate
import com.mt.budgie20.model.RateDb
import com.mt.budgie20.model.User
import com.mt.budgie20.repository.DataStoreRepository
import com.mt.budgie20.repository.DatabaseRepository
import com.mt.budgie20.repository.RemoteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val rpDataStore: DataStoreRepository = DataStoreRepository(application)
    private var rpDatabase: DatabaseRepository
    private var rpRemote: RemoteRepository
    private val rateLive = MutableLiveData<Rate?>()
    private var rateDb: RateDb = RateDb(0, "", 1.0)

    init {

        val userDao = ExpenseDatabase.getDatabase(application).userDao()
        val expenseDao = ExpenseDatabase.getDatabase(application).expenseDao()
        val rateDao = ExpenseDatabase.getDatabase(application).rateDao()
        rpDatabase = DatabaseRepository(userDao, expenseDao, rateDao)
        rpRemote = RemoteRepository()
    }

    private fun insertRate(rateDb: RateDb) = viewModelScope.launch {
        rpDatabase.insertRate(rateDb)
    }

    fun getRate(tip: String) {
        rpRemote.getTryToUsd(tip) { isSuccess, response ->
            if (isSuccess) {
                when (tip) {
                    "EUR_TRY" -> {
                        rateLive.value = response
                        rateDb.rateId = 0
                        rateDb.rateLabel = tip
                        rateDb.rateValue = response!!.rate
                        insertRate(rateDb)
                    }
                    "USD_TRY" -> {
                        rateLive.value = response
                        rateDb.rateId = 1
                        rateDb.rateLabel = tip
                        rateDb.rateValue = response!!.rate
                        insertRate(rateDb)
                    }
                    "GBP_TRY" -> {
                        rateLive.value = response
                        rateDb.rateId = 2
                        rateDb.rateLabel = tip
                        rateDb.rateValue = response!!.rate
                        insertRate(rateDb)
                    }
                    "EUR_USD" -> {
                        rateLive.value = response
                        rateDb.rateId = 3
                        rateDb.rateLabel = tip
                        rateDb.rateValue = response!!.rate
                        insertRate(rateDb)
                    }
                    "EUR_GBP" -> {
                        rateLive.value = response
                        rateDb.rateId = 4
                        rateDb.rateLabel = tip
                        rateDb.rateValue = response!!.rate
                        insertRate(rateDb)
                    }
                    "USD_GBP" -> {
                        rateLive.value = response
                        rateDb.rateId = 5
                        rateDb.rateLabel = tip
                        rateDb.rateValue = response!!.rate
                        insertRate(rateDb)
                    }
                }
            }
        }
    }

    private val readFromDataStore = rpDataStore.onBoardStatus
    val readStatus = readFromDataStore.asLiveData()
    val getAllExpense = rpDatabase.readAllExpenses

    val getAllUser = rpDatabase.readAllUser

    val readAllRates = rpDatabase.readAllRates

    fun insertUser(user: User) = viewModelScope.launch(Dispatchers.IO) {
        rpDatabase.insertUser(user)
    }

    fun insertExpense(expense: Expense) = viewModelScope.launch {
        rpDatabase.insertExpense(expense)
    }

    fun removeExpense(expense: Expense) = viewModelScope.launch {
        rpDatabase.deleteExpense(expense)
    }

    fun getExpenseId(expenseId: Int) = rpDatabase.findExpenseById(expenseId)

    fun saveToDataStore(status: Boolean) = viewModelScope.launch(Dispatchers.IO) {
        rpDataStore.updateShowCompleted(status)
    }
}