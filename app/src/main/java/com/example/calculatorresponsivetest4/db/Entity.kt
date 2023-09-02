package com.example.calculatorresponsivetest4.db

import androidx.room.ColumnInfo
import androidx.room.PrimaryKey

@androidx.room.Entity(tableName = "home_history_table")
data class Entity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "History_ID")
    val history_id: Int,
    @ColumnInfo(name = "Calculator_History")
    val history: String
)