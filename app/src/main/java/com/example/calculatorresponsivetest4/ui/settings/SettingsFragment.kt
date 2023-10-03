package com.example.calculatorresponsivetest4.ui.settings

import android.os.Bundle
import android.view.ContextThemeWrapper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.calculatorresponsivetest4.R
import com.example.calculatorresponsivetest4.databinding.FragmentSettingsBinding
import com.jaredrummler.android.colorpicker.ColorPickerDialog
import com.jaredrummler.android.colorpicker.ColorPreference

class SettingsFragment : Fragment() {
    private lateinit var binding: FragmentSettingsBinding
    private lateinit var helperClass: HelperClass

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        val binding = FragmentSettingsBinding.inflate(inflater, container, false)
        helperClass = HelperClass()

        val colorPreference = helperClass.findPreference("default_color")

        colorPreference?.setOnPreferenceChangeListener { _, newValue ->
            val selectedColor = newValue as Int
            val hexColor = String.format("#%06X", 0xFFFFFF and selectedColor)
            // Use hexColor as needed
            true
        }

        binding.apply {
            ivChangeColor.setOnClickListener {
                ColorPickerDialog.newBuilder()
                    .setColor(requireContext().resources.getColor(R.color.operator))
                    .show(requireActivity())
            }
        }

        return binding.root
    }
}