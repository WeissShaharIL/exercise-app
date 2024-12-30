package com.example.exerciseapp.data

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(entities = [ExerciseLog::class, Exercise::class], version = 1, exportSchema = false)
abstract class ExerciseLogDatabase : RoomDatabase() {
    abstract fun exerciseLogDao(): ExerciseLogDao
    abstract fun exerciseDao(): ExerciseDao

    companion object {
        @Volatile
        private var INSTANCE: ExerciseLogDatabase? = null

        fun getInstance(context: Context): ExerciseLogDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ExerciseLogDatabase::class.java,
                    "exercise_log_database"
                ).addCallback(object : Callback() {
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)


                        val defaultExercises = listOf(
                            Exercise(name = "Push-ups"),
                            Exercise(name = "Squats"),
                            Exercise(name = "Sit-ups")
                        )
                        CoroutineScope(Dispatchers.IO).launch {
                            getInstance(context).exerciseDao().apply {
                                defaultExercises.forEach { insertExercise(it) }
                            }
                        }
                    }
                }).fallbackToDestructiveMigration().build()
                INSTANCE = instance
                instance
            }
        }
    }
}
