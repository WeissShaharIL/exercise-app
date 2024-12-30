package com.example.exerciseapp.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [ExerciseLog::class], version = 1, exportSchema = false)
abstract class ExerciseLogDatabase : RoomDatabase() {
    abstract fun exerciseLogDao(): ExerciseLogDao

    companion object {
        @Volatile
        private var INSTANCE: ExerciseLogDatabase? = null

        fun getInstance(context: Context): ExerciseLogDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ExerciseLogDatabase::class.java,
                    "exercise_log_database"
                ).fallbackToDestructiveMigration().build()
                INSTANCE = instance
                instance
            }
        }
    }
}
