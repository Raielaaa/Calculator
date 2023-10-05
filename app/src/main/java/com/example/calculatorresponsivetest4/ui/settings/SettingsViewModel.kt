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


class SettingsViewModel() : ViewModel() {
    fun updateFont(selectedFont: String) {
        val fontResourceID = when (selectedFont) {
            "Acme" -> R.font.acme
            "Open sans" -> R.font.open_sans_regular
            "Poppins" -> R.font.poppins_regular
            "Roboto" -> R.font.roboto_regular
            else -> R.font.acme // Default font
        }

//        val newFont = ResourcesCompat.getFont(context, fontResourceID)
//        activity.theme.applyStyle(R.font.poppins_regular, true)
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
}