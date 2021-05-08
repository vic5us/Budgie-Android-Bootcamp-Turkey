package com.mt.budgie20.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.mt.budgie20.model.RateDb
import com.mt.budgie20.model.Expense
import com.mt.budgie20.model.User

@Database(
    entities = [Expense::class, User::class, RateDb::class],
    version = 3,
    exportSchema = false
)
abstract class ExpenseDatabase : RoomDatabase() {

    abstract fun expenseDao(): ExpenseDao
    abstract fun userDao(): UserDao
    abstract fun rateDao(): RateDao

    companion object {
        @Volatile
        private var INSTANCE: ExpenseDatabase? = null

        fun getDatabase(context: Context): ExpenseDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context,
                    ExpenseDatabase::class.java,
                    "expense_db"
                )
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}