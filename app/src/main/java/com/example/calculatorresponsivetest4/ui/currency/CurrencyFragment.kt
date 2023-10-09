package com.example.calculatorresponsivetest4.ui.currency

import android.annotation.SuppressLint
import android.app.Dialog
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.calculatorresponsivetest4.R
import com.example.calculatorresponsivetest4.databinding.FragmentCurrencyBinding
import com.example.calculatorresponsivetest4.db.DBViewModel
import com.example.calculatorresponsivetest4.db.DBViewModelFactory
import com.example.calculatorresponsivetest4.db.Database
import com.example.calculatorresponsivetest4.ui.converter.ConverterViewModel
import com.example.calculatorresponsivetest4.ui.settings.SettingsViewModel
import com.example.calculatorresponsivetest4.utils.CustomVibrate
import java.lang.StringBuilder

class CurrencyFragment : Fragment() {

    private var _binding: FragmentCurrencyBinding? = null
    private lateinit var converterViewModel: ConverterViewModel
    private lateinit var currencyViewModel: CurrencyViewModel
    private lateinit var settingsViewModel: SettingsViewModel
    private lateinit var dbViewModel: DBViewModel
    private lateinit var dialog: Dialog
    private lateinit var buttonFromDialog: Button

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
        settingsViewModel = ViewModelProvider(this)[SettingsViewModel::class.java]
        dbViewModel = ViewModelProvider(this, DBViewModelFactory(Database.getInstance(requireContext()).dao()))[DBViewModel::class.java]
        dialog = Dialog(requireActivity())

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

    @SuppressLint("UseCompatLoadingForDrawables")
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
                if (!currencyViewModel.isNetworkAvailable(requireContext())) {
                    dialog.apply {
                        setContentView(R.layout.custom_dailog)

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
                            window!!.setBackgroundDrawable(requireActivity().resources.getDrawable(R.drawable.custom_dialog_background))
                        window!!.setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                        setCancelable(false)
                        window!!.attributes.windowAnimations = R.style.animation
                        show()
                    }
                    buttonFromDialog = dialog.findViewById<Button>(R.id.btnOk)
                    buttonFromDialog.setOnClickListener {
                        dialog.dismiss()
                        findNavController().navigate(R.id.nav_home)
                    }
                } else {
                    try {
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
                    } catch (ignored: Exception) { }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}