package com.example.calculatorresponsivetest4.db

import androidx.lifecycle.LiveData
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@androidx.room.Dao
interface Dao {
    @Insert
    suspend fun insertEntity(entity: Entity)

    @Delete
    suspend fun deleteEntity(entity: Entity)

    @Query("SELECT * FROM home_history_table")
    fun getAllEntity(): LiveData<List<Entity>>

    @Query("DELETE FROM home_history_table")
    fun deleteAllEntity()
}