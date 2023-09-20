package com.example.calculatorresponsivetest4.ui.converter

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.calculatorresponsivetest4.R
import com.example.calculatorresponsivetest4.databinding.FragmentConverterBinding

class ConverterFragment : Fragment() {

    private lateinit var pairValueUnitFrom: Pair<String, String>
    private lateinit var pairValueUnitTo: Pair<String, String>
    private lateinit var converterViewModel: ConverterViewModel
    private lateinit var adapter: ConverterItemAdapter
    private lateinit var spinnerInputFrom: Spinner
    private lateinit var spinnerInputTo: Spinner
    private var _binding: FragmentConverterBinding? = null
    private var updatedNumInput: Double = 0.0
    private var positionFrom: String = ""
    private var positionTo: String = ""
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        converterViewModel = ViewModelProvider(this)[ConverterViewModel::class.java]
        _binding = FragmentConverterBinding.inflate(inflater, container, false)
        val root: View = binding.root

        spinnerInputFrom = binding.spinnerInputFrom
        spinnerInputTo = binding.spinnerInputTo
        initRecyclerView()
        initSpinner(spinnerInputFrom)
        initSpinner(spinnerInputTo)
        initInputFromAndToFunction()

        return root
    }

    private fun initInputFromAndToFunction() {
        binding.apply {
            etNumInputFrom.addTextChangedListener(object : TextWatcher {

                // This method is called before the text changes.
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) { }

                // This method is called when the text changes.
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    try {
                        updatedNumInput = s.toString().toDoubleOrNull() ?: 0.0
                        initDisplayAnswer(binding.spinnerInputFrom)
                    } catch (ignored: Exception) {}
                }

                // This method is called after the text changes.
                override fun afterTextChanged(s: Editable?) { }
            })
        }
    }

    private fun initRecyclerView() {
        adapter = ConverterItemAdapter(ConverterItems.converterItemList, binding.rvTopSelection, requireContext(), spinnerInputFrom, spinnerInputTo) {
            converterItemModel: ConverterItemModel, spinnerInputFrom: Spinner, spinnerInputTo: Spinner, context: Context ->
            converterViewModel.initializeSpinnerContent(converterItemModel, spinnerInputFrom, spinnerInputTo, context)
        }
        binding.rvTopSelection.adapter = adapter
    }

    private fun initSpinner(spinnerInput: Spinner) {
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.converter_input_list,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            spinnerInput.adapter = adapter
            spinnerInput.setSelection(0)
            spinnerInput.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    try {
                        when (spinnerInput.id) {
                            R.id.spinnerInputFrom -> {
                                positionFrom = spinnerInput.getItemAtPosition(position).toString()
                                pairValueUnitFrom = converterViewModel.initializeSelectedSpinnerItemID(
                                    if (positionFrom == "") "(nm)" else positionFrom,
                                    if (positionTo == "") "(nm)" else positionTo)
                            }
                            R.id.spinnerInputTo -> {
                                positionTo = spinnerInput.getItemAtPosition(position).toString()
                                pairValueUnitTo = converterViewModel.initializeSelectedSpinnerItemID(
                                    if (positionFrom == "") "(nm)" else positionFrom,
                                    if (positionTo == "") "(nm)" else positionTo)
                            }
                        }
                        initDisplayAnswer(spinnerInput)
                    } catch (ignored: Exception) {}
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                    // Your code for handling nothing selected goes here
                }
            }
        }
    }

    private fun initDisplayAnswer(spinnerInput: Spinner) {
        binding.apply {
            val selectedInput = spinnerInput.selectedItem.toString()

            val conversionAnswer = when {
                //  LENGTH
                resources.getStringArray(R.array.converter_input_list).contains(selectedInput) ->
                    UnitConverter.getLengthConversionAnswer(updatedNumInput, pairValueUnitFrom, pairValueUnitTo)
                //  ENERGY
                resources.getStringArray(R.array.converter_input_list_energy).contains(selectedInput) ->
                    UnitConverter.getEnergyConversionAnswer(updatedNumInput, spinnerInputFrom.selectedItem, spinnerInputTo.selectedItem)
                //  PRESSURE
                resources.getStringArray(R.array.converter_input_list_pressure).contains(selectedInput) ->
                    UnitConverter.getPressureConversionAnswer(updatedNumInput, spinnerInputFrom.selectedItem, spinnerInputTo.selectedItem)
                //  SPEED
                resources.getStringArray(R.array.converter_input_list_speed).contains(selectedInput) ->
                    UnitConverter.getSpeedConversionAnswer(updatedNumInput, spinnerInputFrom.selectedItem, spinnerInputTo.selectedItem)
                //  ANGLE
                resources.getStringArray(R.array.converter_input_list_angle).contains(selectedInput) ->
                    UnitConverter.getAngleConversionAnswer(updatedNumInput, spinnerInputFrom.selectedItem, spinnerInputTo.selectedItem)
                // MASS
                resources.getStringArray(R.array.converter_input_list_mass).contains(selectedInput) ->
                    UnitConverter.getMassConversionAnswer(updatedNumInput, spinnerInputFrom.selectedItem, spinnerInputTo.selectedItem)
                //  TEMPERATURE
                resources.getStringArray(R.array.converter_input_list_temperature).contains(selectedInput) ->
                    UnitConverter.getTemperatureConversionAnswer(updatedNumInput, spinnerInputFrom.selectedItem, spinnerInputTo.selectedItem)

                else -> "Invalid conversion"
            }

            etNumInputTo.setText(conversionAnswer)
        }
    }

    override fun onResume() {
        super.onResume()
        converterViewModel.initSelectedConversionToLength(binding.rvTopSelection, requireContext())
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}