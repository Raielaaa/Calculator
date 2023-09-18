package com.example.calculatorresponsivetest4.ui.converter

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.calculatorresponsivetest4.R
import com.example.calculatorresponsivetest4.databinding.FragmentConverterBinding

class ConverterFragment : Fragment() {

    private var _binding: FragmentConverterBinding? = null
    private lateinit var pairValueUnitFrom: Pair<String, String>
    private lateinit var pairValueUnitTo: Pair<String, String>
    private var updatedNumInput: Double = 0.0
    private var positionFrom: String = ""
    private var positionTo: String = ""
    private lateinit var converterViewModel: ConverterViewModel
    private lateinit var adapter: ConverterItemAdapter
    private lateinit var spinnerInputFrom: Spinner
    private lateinit var spinnerInputTo: Spinner

    // This property is only valid between onCreateView and
    // onDestroyView.
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
                        binding.etNumInputTo.setText(UnitConverter.lengthFormula(updatedNumInput, pairValueUnitFrom, pairValueUnitTo))
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
                                pairValueUnitFrom = initializeSelectedSpinnerItemID(
                                    if (positionFrom == "") "(nm)" else positionFrom,
                                    if (positionTo == "") "(nm)" else positionTo)
                            }
                            R.id.spinnerInputTo -> {
                                positionTo = spinnerInput.getItemAtPosition(position).toString()
                                pairValueUnitTo = initializeSelectedSpinnerItemID(
                                    if (positionFrom == "") "(nm)" else positionFrom,
                                    if (positionTo == "") "(nm)" else positionTo)
                            }
                        }

                        binding.etNumInputTo.setText(UnitConverter.lengthFormula(updatedNumInput, pairValueUnitFrom, pairValueUnitTo))
                    } catch (ignored: Exception) {}
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                    // Your code for handling nothing selected goes here
                }
            }
        }
    }

    private fun initializeSelectedSpinnerItemID(spinnerInputFrom: String, spinnerInputTo: String): Pair<String, String> {
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

    override fun onResume() {
        super.onResume()

        val itemView = binding.rvTopSelection.findViewHolderForAdapterPosition(0)!!.itemView
        val cvMain = itemView.findViewById<CardView>(R.id.cvMain)
        val btnItem = itemView.findViewById<Button>(R.id.btnItem)

        val updatedBGColor = ContextCompat.getColor(requireContext(), R.color.unitConverterClickedItemBGColor)
        cvMain.setCardBackgroundColor(updatedBGColor)
        btnItem.setBackgroundColor(updatedBGColor)
        btnItem.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}