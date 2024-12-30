package com.example.exerciseapp.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface ExerciseDao {
    @Query("SELECT * FROM exercise ORDER BY name ASC")
    fun getAllExercises(): LiveData<List<Exercise>>

    @Insert
    suspend fun insertExercise(exercise: Exercise)
}
