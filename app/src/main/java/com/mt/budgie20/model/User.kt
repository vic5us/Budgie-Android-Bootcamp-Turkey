package com.mt.budgie20.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user")
data class User(
    @PrimaryKey
    val userId: Int,
    @ColumnInfo(name = "name")
    val name : String,
    @ColumnInfo(name = "gender")
    val gender : Int
)