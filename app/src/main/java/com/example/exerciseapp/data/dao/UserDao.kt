package com.example.exerciseapp.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.exerciseapp.data.entities.User


@Dao
interface UserDao {
    @Insert
    suspend fun insertUser(user: User)

    @Query("SELECT * FROM user LIMIT 1")
    suspend fun getUser(): User?

    @Query("SELECT * FROM user ORDER BY id DESC LIMIT 1") // Fetch the last row
    suspend fun getLastUser(): User?

    @Query("SELECT * FROM user")
    suspend fun getAllUsers(): List<User>


}
