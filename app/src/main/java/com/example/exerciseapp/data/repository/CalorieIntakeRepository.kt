package com.example.exerciseapp.data.repository

import androidx.lifecycle.LiveData
import com.example.exerciseapp.data.dao.CalorieIntakeDao
import com.example.exerciseapp.data.entities.CalorieIntake
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CalorieIntakeRepository(private val calorieIntakeDao: CalorieIntakeDao) {

    val allCalorieIntakes: LiveData<List<CalorieIntake>> = calorieIntakeDao.getAllCalorieIntakes()

    suspend fun insertCalorieIntake(calorieIntake: CalorieIntake) {
        withContext(Dispatchers.IO) {
            calorieIntakeDao.insertCalorieIntake(calorieIntake)
        }
    }
    fun getTodayCalorieIntakes(): LiveData<List<CalorieIntake>> {
        return calorieIntakeDao.getTodayCalorieIntakes()
    }
    suspend fun deleteCalorieIntake(id: Int) {
        withContext(Dispatchers.IO) {
            calorieIntakeDao.deleteCalorieIntake(id)
        }
    }
}
