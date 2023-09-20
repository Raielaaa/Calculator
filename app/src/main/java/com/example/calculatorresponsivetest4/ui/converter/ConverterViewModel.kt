package com.example.calculatorresponsivetest4.ui.converter

import android.content.Context
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.RecyclerView
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

    fun initializeSelectedSpinnerItemID(spinnerInputFrom: String, spinnerInputTo: String): Pair<String, String> {
        val unitMap = mapOf(
            "nm" to UnitConverter.nanometer,
            "µm" to UnitConverter.micrometer,
            "mm" to UnitConverter.millimeter,
            "cm" to UnitConverter.centimeter,
            "m)" to UnitConverter.meter,
            "km" to UnitConverter.kilometer,
            "in" to UnitConverter.inch,
            "ft" to UnitConverter.foot,
            "yd" to UnitConverter.yard,
            "mi" to UnitConverter.mile
        )
        val inputFrom = unitMap[spinnerInputFrom.substring(1, 3)] ?: ""
        val inputTo = unitMap[spinnerInputTo.substring(1, 3)] ?: ""
        return Pair(inputFrom, inputTo)
    }

    fun initSelectedConversionToLength(rvTopSelection: RecyclerView, context: Context) {
        val itemView = rvTopSelection.findViewHolderForAdapterPosition(0)!!.itemView
        val cvMain = itemView.findViewById<CardView>(R.id.cvMain)
        val btnItem = itemView.findViewById<Button>(R.id.btnItem)

        val updatedBGColor = ContextCompat.getColor(context, R.color.unitConverterClickedItemBGColor)
        cvMain.setCardBackgroundColor(updatedBGColor)
        btnItem.setBackgroundColor(updatedBGColor)
        btnItem.setTextColor(ContextCompat.getColor(context, R.color.white))
    }
}