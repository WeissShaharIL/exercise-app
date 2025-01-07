package com.example.exerciseapp.data.db

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.exerciseapp.data.dao.*
import com.example.exerciseapp.data.entities.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(
    entities = [ExerciseLog::class, Exercise::class, User::class, CalorieIntake::class],
    version = 2, // Incremented version for schema changes
    exportSchema = false
)
abstract class ExerciseLogDatabase : RoomDatabase() {
    abstract fun exerciseLogDao(): ExerciseLogDao
    abstract fun exerciseDao(): ExerciseDao
    abstract fun userDao(): UserDao
    abstract fun calorieIntakeDao(): CalorieIntakeDao

    companion object {
        @Volatile
        private var INSTANCE: ExerciseLogDatabase? = null

        fun getInstance(context: Context): ExerciseLogDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ExerciseLogDatabase::class.java,
                    "exercise_log_database"
                )
                    .addCallback(object : Callback() {
                        override fun onCreate(db: SupportSQLiteDatabase) {
                            super.onCreate(db)

                            INSTANCE?.let { database ->
                                val defaultExercises = listOf(
                                    Exercise(name = "Push-ups"),
                                    Exercise(name = "Squats"),
                                    Exercise(name = "Sit-ups")
                                )

                                CoroutineScope(Dispatchers.IO).launch {
                                    try {
                                        database.exerciseDao().apply {
                                            defaultExercises.forEach { insertExercise(it) }
                                        }
                                        Log.d("ExerciseLogDatabase", "Default exercises inserted successfully.")
                                    } catch (e: Exception) {
                                        Log.e("ExerciseLogDatabase", "Error inserting default exercises: ${e.message}")
                                    }
                                }
                            }
                        }
                    })
                    .fallbackToDestructiveMigration() // Clears data on version mismatch
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
