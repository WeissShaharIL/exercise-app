package com.example.exerciseapp.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query


@Dao
interface UserDao {
    @Insert
    suspend fun insertUser(user: User)

    @Query("SELECT * FROM user LIMIT 1")
    suspend fun getUser(): User?

    @Query("SELECT * FROM user ORDER BY id DESC LIMIT 1") // Fetch the last row
    suspend fun getLastUser(): User?

}
