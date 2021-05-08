package com.mt.budgie20.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "rates")
data class RateDb(
    @PrimaryKey
    var rateId : Int,
    @ColumnInfo(name = "rateLabel")
    var rateLabel :String,
    @ColumnInfo(name = "rateValue")
    var rateValue : Double
)
