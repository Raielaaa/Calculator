package com.example.calculatorresponsivetest4.ui.settings

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.content.SharedPreferences.Editor
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModel
import com.example.calculatorresponsivetest4.R
import androidx.appcompat.widget.SwitchCompat
import androidx.lifecycle.MutableLiveData


class SettingsViewModel() : ViewModel() {
    val counter: MutableLiveData<Int> by lazy {
        MutableLiveData<Int>().apply {
            value = 1
        }
    }

    fun updateSwitch(swDisplayTheme: SwitchCompat, context: Context) {
        val isDarkMode = context.resources.configuration.uiMode and
                Configuration.UI_MODE_NIGHT_MASK == Configuration.UI_MODE_NIGHT_YES
        swDisplayTheme.isChecked = isDarkMode
        if (isDarkMode) AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        else AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
    }

    fun updateSwitchOnPref(boolFromPref: Boolean, swDisplayTheme: SwitchCompat) {
        swDisplayTheme.isChecked = boolFromPref
        if (boolFromPref) AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        else AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
    }

    fun sharedPrefSwitch(isChecked: Boolean, editor: Editor) {
        if (isChecked) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            editor.apply {
                putBoolean("DarkMode_key", true)
                commit()
            }
        }
        else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            editor.apply {
                putBoolean("DarkMode_key", false)
                commit()
            }
        }
    }

    fun initVibrateSwitch(swVibrate: SwitchCompat, editor: Editor, boolFromPref: Boolean) {
        swVibrate.isChecked = boolFromPref
        swVibrate.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                editor.apply {
                    putBoolean("Vibrate_key", true)
                    commit()
                }
            } else {
                editor.apply {
                    putBoolean("Vibrate_key", false)
                    commit()
                }
            }
        }
    }
}