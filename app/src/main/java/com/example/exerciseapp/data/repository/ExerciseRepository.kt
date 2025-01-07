package com.example.exerciseapp.data.repository

import com.example.exerciseapp.data.dao.ExerciseDao
import com.example.exerciseapp.data.entities.Exercise


class ExerciseRepository(private val exerciseDao: ExerciseDao) {
    val allExercises = exerciseDao.getAllExercises()

    suspend fun insertExercise(exercise: Exercise) {
        exerciseDao.insertExercise(exercise)
    }
}
