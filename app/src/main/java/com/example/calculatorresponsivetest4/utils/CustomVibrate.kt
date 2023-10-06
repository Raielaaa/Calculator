package com.example.calculatorresponsivetest4.utils

import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager

object CustomVibrate {
    @Suppress("DEPRECATION")
    fun vibrate(durationMillis: Long = 50, context: Context) {
        val sharedPreferences = context.getSharedPreferences("SP_Calculator", Context.MODE_PRIVATE)

        if (sharedPreferences.getBoolean("Vibrate_key", false)) {
            when {
                Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
                    // For Android 12 (S) and above
                    val vibratorManager = context.getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as VibratorManager
                    val vibrationEffect = VibrationEffect.createOneShot(durationMillis, VibrationEffect.DEFAULT_AMPLITUDE)
                    val vibrator = vibratorManager.defaultVibrator
                    vibrator.vibrate(vibrationEffect)
                }

                Build.VERSION.SDK_INT >= Build.VERSION_CODES.O && Build.VERSION.SDK_INT < Build.VERSION_CODES.S -> {
                    // For Android 8.0 (Oreo) to Android 11 (R)
                    val vibrator = context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
                    val vibrationEffect = VibrationEffect.createOneShot(durationMillis, VibrationEffect.DEFAULT_AMPLITUDE)
                    vibrator.vibrate(vibrationEffect)
                }

                else -> {
                    // For Android versions below Oreo (API level 26)
                    val vibrator = context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
                    vibrator.vibrate(durationMillis)
                }
            }
        }
    }
}