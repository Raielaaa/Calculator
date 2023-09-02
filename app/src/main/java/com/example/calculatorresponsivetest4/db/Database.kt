package com.example.calculatorresponsivetest4.db

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase

@androidx.room.Database(entities = [Entity::class], version = 1, exportSchema = false)
abstract class Database: RoomDatabase() {
    abstract fun dao(): Dao
    companion object {
        @Volatile
        var INSTANCE: Database? = null
        fun getInstance(context: Context): Database = synchronized(this) {
            return INSTANCE ?: Room.databaseBuilder(
                context.applicationContext,
                Database::class.java,
                "home_history_database"
            ).build()
        }
    }
}