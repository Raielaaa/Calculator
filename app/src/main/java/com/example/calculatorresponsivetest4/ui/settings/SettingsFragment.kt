package com.example.calculatorresponsivetest4.ui.settings

import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.content.SharedPreferences.Editor
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.lifecycle.ViewModelProvider
import com.example.calculatorresponsivetest4.R
import com.example.calculatorresponsivetest4.databinding.FragmentSettingsBinding
import com.jaredrummler.android.colorpicker.ColorPickerDialog

class SettingsFragment : Fragment(), SharedPreferences.OnSharedPreferenceChangeListener {
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
        sharedPreferences.registerOnSharedPreferenceChangeListener(this)
        editor = sharedPreferences.edit()

        val colorPreference = helperClass.findPreference("default_color")

        colorPreference?.setOnPreferenceChangeListener { _, newValue ->
            val selectedColor = newValue as Int
            val hexColor = String.format("#%06X", 0xFFFFFF and selectedColor)
            // Use hexColor as needed

            true
        }

        initViews()
        initClickedFunctions()
        initSpinner()

        return binding.root
    }

    private fun initSpinner() {
        binding.apply {
            val spinnerItems = requireContext().resources.getStringArray(R.array.font_family_list)
            val index = spinnerItems.indexOf(sharedPreferences.getString("Font_string_key", "Acme"))
            spLanguage.setSelection(index)

            spLanguage.onItemSelectedListener = object : OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    val selectedFont = when(parent!!.selectedItem.toString()) {
                        "Acme" -> R.style.MyTheme_Acme
                        "Open sans" -> R.style.MyTheme_OpenSans
                        "Poppins" -> R.style.MyTheme_Poppins
                        "Roboto" -> R.style.MyTheme_Roboto
                        else -> R.style.MyTheme_Acme
                    }
                    requireActivity().setTheme(selectedFont)
                    editor.apply {
                        putInt("Font_key", selectedFont)
                        putString("Font_string_key", parent.selectedItem.toString())
                        commit()
                    }
                    Toast.makeText(requireContext(), "Restart the app to apply changes", Toast.LENGTH_SHORT).show()
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
//                    TODO("Not yet implemented")
                }
            }
        }
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

            val hexColor = sharedPreferences.getString("Color_key", "#eb6767") // Provide a default color if needed
            ivChangeColor.setCardBackgroundColor(Color.parseColor(hexColor))
        }

    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences?, key: String?) {
        // This method will be called whenever a preference value changes
        if (key == "Color_key") {
            // Handle the change of "Color_key" preference
            val newColor = sharedPreferences!!.getString(key, "#000000")
            binding.ivChangeColor.setCardBackgroundColor(Color.parseColor(newColor))
        } else {
            Log.d("MyTag", "onSharedPreferenceChanged: not found")
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        sharedPreferences.unregisterOnSharedPreferenceChangeListener(this)
    }
}