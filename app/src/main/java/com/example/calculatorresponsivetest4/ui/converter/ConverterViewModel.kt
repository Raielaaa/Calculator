package com.example.calculatorresponsivetest4.ui.converter

import android.content.Context
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.lifecycle.ViewModel
import com.example.calculatorresponsivetest4.R

class ConverterViewModel : ViewModel() {
    fun initializeSpinnerContent(converterItemModel: ConverterItemModel, spinnerInputFrom: Spinner, spinnerInputTo: Spinner, context: Context) {
        when (converterItemModel.listItemName.uppercase()) {
            "LENGTH" -> {
                initSpinnerStringArray(context, R.array.converter_input_list, spinnerInputFrom)
                initSpinnerStringArray(context, R.array.converter_input_list, spinnerInputTo)
            }
            "MASS" -> {
                initSpinnerStringArray(context, R.array.converter_input_list_mass, spinnerInputFrom)
                initSpinnerStringArray(context, R.array.converter_input_list_mass, spinnerInputTo)
            }
            "SPEED" -> {
                initSpinnerStringArray(context, R.array.converter_input_list_speed, spinnerInputFrom)
                initSpinnerStringArray(context, R.array.converter_input_list_speed, spinnerInputTo)
            }
            "TEMPERATURE" -> {
                initSpinnerStringArray(context, R.array.converter_input_list_temperature, spinnerInputFrom)
                initSpinnerStringArray(context, R.array.converter_input_list_temperature, spinnerInputTo)
            }
            "ANGLE" -> {
                initSpinnerStringArray(context, R.array.converter_input_list_angle, spinnerInputFrom)
                initSpinnerStringArray(context, R.array.converter_input_list_angle, spinnerInputTo)
            }
            "PRESSURE" -> {
                initSpinnerStringArray(context, R.array.converter_input_list_pressure, spinnerInputFrom)
                initSpinnerStringArray(context, R.array.converter_input_list_pressure, spinnerInputTo)
            }
            "ENERGY" -> {
                initSpinnerStringArray(context, R.array.converter_input_list_energy, spinnerInputFrom)
                initSpinnerStringArray(context, R.array.converter_input_list_energy, spinnerInputTo)
            }
        }
    }

    private fun initSpinnerStringArray(context: Context, stringArrayFromResource: Int, spinnerInput: Spinner) {
        ArrayAdapter.createFromResource(
            context,
            stringArrayFromResource,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            spinnerInput.adapter = adapter
            spinnerInput.setSelection(0)
//            spinnerInput.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
//                override fun onItemSelected(
//                    parent: AdapterView<*>?,
//                    view: View?,
//                    position: Int,
//                    id: Long
//                ) {
//                }
//
//                override fun onNothingSelected(parent: AdapterView<*>?) {
//                    // Your code for handling nothing selected goes here
//                }
//            }
        }
    }
}