package com.mt.budgie20.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mt.budgie20.model.RateDb

@Dao
interface RateDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(rateDb : RateDb)

    @Query("SELECT * FROM  rates")
    fun getAllRates() : LiveData<List<RateDb>>

    @Query("select * from rates where rateLabel = :rateLabel")
    suspend fun getRateByLabel(rateLabel : String) : RateDb
}