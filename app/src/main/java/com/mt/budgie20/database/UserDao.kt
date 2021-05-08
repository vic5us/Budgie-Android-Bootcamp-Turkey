package com.mt.budgie20.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mt.budgie20.model.User

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(user : User)

    @Query("SELECT * FROM user ORDER BY userId ASC")
    fun getAllUser() :LiveData<List<User>>

    @Query("select * from user where userId = :userId")
    suspend fun getUserById(userId : Int) : User
}