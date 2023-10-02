package com.example.calculatorresponsivetest4.db

import androidx.room.ColumnInfo
import androidx.room.PrimaryKey

@androidx.room.Entity(tableName = "home_history_table")
data class Entity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "History_ID")
    val historyId: Int,
    @ColumnInfo(name = "Calculator_Solution")
    val solution: String,
    @ColumnInfo(name = "Calculator_Answer")
    val answer: String,
    @ColumnInfo(name = "Calculator_Mode")
    val mode: String,
    @ColumnInfo(name = "Current_Date_Time")
    val currentDateTime: String
)