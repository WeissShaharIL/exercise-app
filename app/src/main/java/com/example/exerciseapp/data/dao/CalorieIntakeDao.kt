package com.example.exerciseapp.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.exerciseapp.data.entities.CalorieIntake

@Dao
interface CalorieIntakeDao {
    @Query("SELECT * FROM calorie_intake ORDER BY timestamp DESC")
    fun getAllCalorieIntakes(): LiveData<List<CalorieIntake>>

    @Insert
    suspend fun insertCalorieIntake(calorieIntake: CalorieIntake)
    @Query("SELECT * FROM calorie_intake WHERE date(timestamp / 1000, 'unixepoch') = date('now') ORDER BY timestamp DESC")
    fun getTodayCalorieIntakes(): LiveData<List<CalorieIntake>>

    @Query("DELETE FROM calorie_intake WHERE id = :id")
    suspend fun deleteCalorieIntake(id: Int)
}
