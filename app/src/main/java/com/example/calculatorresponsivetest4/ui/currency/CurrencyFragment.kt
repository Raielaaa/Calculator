package com.example.calculatorresponsivetest4.ui.currency

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.calculatorresponsivetest4.databinding.FragmentCurrencyBinding
import com.example.calculatorresponsivetest4.db.DBViewModel
import com.example.calculatorresponsivetest4.db.DBViewModelFactory
import com.example.calculatorresponsivetest4.db.Database
import com.example.calculatorresponsivetest4.ui.converter.ConverterViewModel
import com.example.calculatorresponsivetest4.utils.CustomVibrate
import java.lang.StringBuilder

class CurrencyFragment : Fragment() {

    private var _binding: FragmentCurrencyBinding? = null
    private lateinit var converterViewModel: ConverterViewModel
    private lateinit var currencyViewModel: CurrencyViewModel
    private lateinit var dbViewModel: DBViewModel

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        converterViewModel = ViewModelProvider(this)[ConverterViewModel::class.java]
        currencyViewModel = ViewModelProvider(this)[CurrencyViewModel::class.java]
        dbViewModel = ViewModelProvider(this, DBViewModelFactory(Database.getInstance(requireContext()).dao()))[DBViewModel::class.java]

        _binding = FragmentCurrencyBinding.inflate(inflater, container, false)
        val root: View = binding.root

        initButtons()
        initObservers()

        return root
    }

    private fun initObservers() {
        binding.apply {
            currencyViewModel.convertedRate.observe(viewLifecycleOwner) {
                tvCurrencyTo.text = it.toString()
            }
            currencyViewModel.dateFromApiCall.observe(viewLifecycleOwner) {
                tvCurrencyDate.text = "Last updated : $it"
            }
        }
    }

    private fun initButtons() {
        binding.apply {
            converterViewModel.initButtons(
                btnZero,
                btnOne,
                btnTwo,
                btnThree,
                btnFour,
                btnFive,
                btnSix,
                btnSeven,
                btnEight,
                btnNine,
                btnDot,
                btnReturn,
                etCurrencyFrom,
                requireContext()
            )

            btnClear.setOnClickListener {
                CustomVibrate.vibrate(50, requireContext())
                etCurrencyFrom.setText("")
                tvCurrencyTo.text = "0"
                converterViewModel.displayedText.value = StringBuilder("")
            }

            btnConvert.setOnClickListener {
                CustomVibrate.vibrate(50, requireContext())
                currencyViewModel.getConvertedCurrency(
                    spCurrencyFrom.selectedItem.toString(),
                    spCurrencyTo.selectedItem.toString(),
                    etCurrencyFrom.text.toString().toDouble(),
                    this@CurrencyFragment,
                    progressBar,
                    tvCurrencyDate,
                    dbViewModel
                )
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}