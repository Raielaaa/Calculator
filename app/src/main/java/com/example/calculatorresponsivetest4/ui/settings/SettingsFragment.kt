package com.example.calculatorresponsivetest4.ui.settings

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.content.SharedPreferences.Editor
import android.content.res.Configuration
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import android.view.ContextThemeWrapper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.preference.PreferenceManager
import com.example.calculatorresponsivetest4.R
import com.example.calculatorresponsivetest4.databinding.FragmentSettingsBinding
import com.jaredrummler.android.colorpicker.ColorPickerDialog
import com.jaredrummler.android.colorpicker.ColorPreference

class SettingsFragment : Fragment() {
    private lateinit var binding: FragmentSettingsBinding
    private lateinit var helperClass: HelperClass
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var editor: Editor
    private lateinit var settingsViewModel: SettingsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        settingsViewModel = ViewModelProvider(this@SettingsFragment)[SettingsViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentSettingsBinding.inflate(inflater, container, false)
        helperClass = HelperClass()
        sharedPreferences = requireContext().getSharedPreferences("SP_Calculator", MODE_PRIVATE)
        editor = sharedPreferences.edit()

        val colorPreference = helperClass.findPreference("default_color")

        colorPreference?.setOnPreferenceChangeListener { _, newValue ->
            val selectedColor = newValue as Int
            val hexColor = String.format("#%06X", 0xFFFFFF and selectedColor)
            // Use hexColor as needed
            binding.ivChangeColor.setCardBackgroundColor(Color.parseColor(hexColor))
            editor.apply {
                putString("Color_key", hexColor)
                commit()
            }

            true
        }

        initViews()
        initClickedFunctions()

        return binding.root
    }

    private fun initClickedFunctions() {
        binding.apply {
            ivChangeColor.setOnClickListener {
                ColorPickerDialog.newBuilder()
                    .setColor(requireContext().resources.getColor(R.color.operator))
                    .show(requireActivity())
            }

            swDisplayTheme.setOnCheckedChangeListener { buttonView, isChecked ->
                settingsViewModel.sharedPrefSwitch(isChecked, editor)
            }
        }
        settingsViewModel.updateSwitchOnPref(boolFromPref = sharedPreferences.getBoolean("DarkMode_key", false),
            swDisplayTheme = binding.swDisplayTheme)
        settingsViewModel.initVibrateSwitch(swVibrate = binding.swVibrate, editor = editor,
            boolFromPref = sharedPreferences.getBoolean("Vibrate_key", false))
    }

    private fun initViews() {
        binding.apply {
            settingsViewModel.updateSwitch(swDisplayTheme, requireContext())

            Log.d("MyTag", "initViews: ${R.color.operator}")
            Log.d("MyTag", "initViews_sp: ${sharedPreferences.getString("Color_key", "#${R.color.operator}")}")
            ivChangeColor.setCardBackgroundColor(Color.parseColor(sharedPreferences.getString("Color_key", "#${R.color.operator}")))
        }
    }
}