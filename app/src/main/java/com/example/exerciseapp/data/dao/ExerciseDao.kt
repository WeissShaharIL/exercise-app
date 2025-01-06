package com.example.exerciseapp.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.exerciseapp.data.entities.Exercise

@Dao
interface ExerciseDao {
    @Query("SELECT * FROM exercise ORDER BY name ASC")
    fun getAllExercises(): LiveData<List<Exercise>>

    @Insert
    suspend fun insertExercise(exercise: Exercise)
}
