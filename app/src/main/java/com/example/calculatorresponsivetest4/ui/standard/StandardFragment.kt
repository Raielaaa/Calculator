package com.example.calculatorresponsivetest4.ui.standard

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.calculatorresponsivetest4.R
import com.example.calculatorresponsivetest4.databinding.FragmentHomeBinding
import com.example.calculatorresponsivetest4.db.DBViewModel
import com.example.calculatorresponsivetest4.db.DBViewModelFactory
import com.example.calculatorresponsivetest4.db.Database
import com.example.calculatorresponsivetest4.db.Entity
import com.google.android.material.snackbar.Snackbar
import java.text.DecimalFormat
import javax.script.ScriptEngineManager

class StandardFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var homeViewModel: StandardViewModel
    private lateinit var decimalFormat: DecimalFormat
    private lateinit var dbViewModel: DBViewModel
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor
    private val inputtedValue: StringBuilder = StringBuilder()
    private val answer: StringBuilder = StringBuilder()
    private val homeCurrentHistory: StringBuilder = StringBuilder()
    private var pointUsageIdentifier: Boolean = true

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        initializeViewModels()
        initializeDecimalFormat()
        initializeSharedPreferences()
        setupObservers()
        configureTextViews()
        configureSpecialButtons()
        configureNumKeyLogger()
        configureMemoryButtons()
        initializeTvAnswer()

        return root
    }

    private fun configureMemoryTextView(isAnswerPresent: Boolean, memoryTextView: TextView) {
        if (!isAnswerPresent) {
            binding.apply {
                memoryTextView.setTextColor(ContextCompat.getColor(requireContext(), R.color.disabledColor))
                memoryTextView.isLongClickable = false
                memoryTextView.isClickable = false
            }
        } else {
            binding.apply {
                memoryTextView.setTextColor(ContextCompat.getColor(requireContext(), R.color.enabledColor))
                memoryTextView.isLongClickable = true
                memoryTextView.isClickable = true
            }
        }
    }

    private fun configureMemoryButtons() {
        binding.apply {
            editor.apply {
                tvMC.setOnClickListener {
                    configureMemoryTextView(false, tvMR)
                    putString("Memory", "0")
                    apply()
                    tvMemoryMode.text = "MC  |  ${sharedPreferences.getString("Memory", "0")}"
                }
                tvMR.setOnClickListener {
                    try {
                        homeViewModel.isMemoryButtonSelected.value = true
                        if (sharedPreferences.getString("Memory", "0").toString() != "0") {
                            val retrievedValue: String? = sharedPreferences.getString("Memory", "0")
                            homeViewModel.answerFromVM.value = ""
                            inputtedValue.setLength(0)
                            inputtedValue.append(retrievedValue)
                            homeViewModel.initialEquation.value = inputtedValue.toString()
                            tvMemoryMode.text = "MR  |  ${sharedPreferences.getString("Memory", "0")}"
                        }
                    } catch (ignored: Exception) { }
                }
                tvMS.setOnClickListener {
                    try {
                        if (homeViewModel.isEqualsSelected.value == true) {
                            putString("Memory",
                                if (!homeViewModel.answerFromVM.value.isNullOrEmpty()) homeViewModel.answerFromVM.value!!.replace("=", "").replace(" ", "") else "0"
                            )
                            apply()
                            tvMemoryMode.text = "MS  |  ${sharedPreferences.getString("Memory", "0")}"
                        }
                    } catch (ignored: Exception) { }
                }
                tvMPlus.setOnClickListener {
                    try {
                        if (homeViewModel.isEqualsSelected.value == true) {
                            val numberToBeConfigured: Double = homeViewModel.answerFromVM.value!!.replace("=", "").replace(" ", "").replace(",", "").toDouble()
                            val configuredNumber: Double = sharedPreferences.getString("Memory", "0")!!.replace(",", "").toDouble() + numberToBeConfigured
                            putString("Memory", decimalFormat.format(configuredNumber).toString())
                            apply()
                            tvMemoryMode.text = "M+  |  ${sharedPreferences.getString("Memory", "0")}"
                        }
                    } catch (ignored: Exception) { }
                }
                tvMMinus.setOnClickListener {
                    try {
                        if (homeViewModel.isEqualsSelected.value == true) {
                            val numberToBeConfigured: Double = homeViewModel.answerFromVM.value!!.replace("=", "").replace(" ", "").replace(",", "").toDouble()
                            val configuredNumber: Double = sharedPreferences.getString("Memory", "0")!!.replace(",", "").toDouble() - numberToBeConfigured
                            putString("Memory", decimalFormat.format(configuredNumber).toString())
                            apply()
                            tvMemoryMode.text = "M-  |  ${sharedPreferences.getString("Memory", "0")}"
                        }
                    } catch (ignored: Exception) { }
                }
            }
        }
    }

    private fun initializeTvAnswer() = binding.apply { if (tvAnswer.text == "= ") tvAnswer.text = "= 0" }

    private fun initializeViewModels() {
        homeViewModel = ViewModelProvider(this)[StandardViewModel::class.java]
        dbViewModel = ViewModelProvider(this, DBViewModelFactory(Database.getInstance(requireContext()).dao()))[DBViewModel::class.java]
    }

    private fun initializeDecimalFormat() { decimalFormat = DecimalFormat(resources.getString(R.string.NumberFormat)) }

    private fun initializeSharedPreferences() {
        sharedPreferences = requireActivity().getSharedPreferences("CalculatorApp_CalculatorResponsiveTest4_SP", Context.MODE_PRIVATE)
        editor = sharedPreferences.edit()
    }

    private fun setupObservers() {
        binding.apply {
            homeViewModel.initialEquation.observe(viewLifecycleOwner) {
                try {
                    if (it[it.length - 1].toString() == ".") {
                        inputtedValue.setLength(0)
                        inputtedValue.append(it)
                        tvSolution.text = inputtedValue
                    } else {
                        val formattedExpression = evaluateExpression(it)
                        tvSolution.text = formattedExpression
                    }
                } catch (e: Exception) {
                    inputtedValue.setLength(0)
                    tvSolution.text = ""
                }
            }

            homeViewModel.answerFromVM.observe(viewLifecycleOwner) {
                tvAnswer.text = if (!it.isNullOrEmpty()) "= $it" else ""
                if (it.isEmpty()) {
                    configureMemoryTextView(false, tvMPlus)
                    configureMemoryTextView(false, tvMMinus)
                }
            }

            homeViewModel.isEqualsSelected.observe(viewLifecycleOwner) {
                when (it) {
                    true -> {
                        configureMemoryTextView(true, tvMC)
                        configureMemoryTextView(true, tvMR)
                        configureMemoryTextView(true, tvMPlus)
                        configureMemoryTextView(true, tvMMinus)
                    }
                    false -> {
                        configureMemoryTextView(false, tvMC)
                        configureMemoryTextView(false, tvMR)
                        configureMemoryTextView(false, tvMPlus)
                        configureMemoryTextView(false, tvMMinus)
                    }
                }
            }
//            homeViewModel.recentHistory.observe(viewLifecycleOwner) { tvHistory?.text = it }
        }
    }

    private fun evaluateExpression(expression: String): String {
        val formattedExpression = StringBuilder()
        val currentNumber = StringBuilder()

        for (i in expression.indices) {
            val currentChar = expression[i]

            if (currentChar.isDigit() || currentChar == '.') {
                currentNumber.append(currentChar)
            } else {
                if (currentNumber.isNotEmpty()) {
                    val formattedNumber = decimalFormat.format(currentNumber.toString().toDouble())
                    formattedExpression.append(formattedNumber)
                    currentNumber.clear()
                }
                formattedExpression.append(currentChar)
            }
        }

        if (currentNumber.isNotEmpty()) {
            val formattedNumber = decimalFormat.format(currentNumber.toString().toDouble())
            formattedExpression.append(formattedNumber)
        }

        return formattedExpression.toString()
    }

    private fun configureTextViews() {
        binding.apply {
            tvSolution.movementMethod = ScrollingMovementMethod()
            tvSolution.setHorizontallyScrolling(true)
        }
    }

    private fun configureSpecialButtons() {
        binding.apply {
            homeViewModel.apply {
                btnPoint.setOnClickListener {
                    if (pointUsageIdentifier) {
                        inputtedValue.append(resources.getString(R.string.dot))
                        initialEquation.value = inputtedValue.toString()
                        pointUsageIdentifier = false
                    } else { Snackbar.make(requireView(), "Error. Invalid input", Snackbar.LENGTH_SHORT).show() }
                }

                btnPercent.setOnClickListener {
                    if (tvAnswer.text.isNotEmpty() && tvSolution.text.isNotEmpty()) {
                        inputtedValue.setLength(0)
                        inputtedValue.append(tvAnswer.text.toString().replace("=", "") + " ${resources.getString(R.string.percent)} ")
                        initialEquation.value = inputtedValue.toString()
                        isEqualsSelected.value = false
                    } else {
                        if (isPercentUsed.value == false) {
                            isPercentUsed.value = true
                            if (tvAnswer.text.toString() != "") {
                                inputtedValue.apply {
                                    append(answerFromVM.value).append(" ${resources.getString(R.string.percent)} ")
                                }
                                initialEquation.value = inputtedValue.toString()
                                isEqualsSelected.value = false
                            } else {
                                inputtedValue.append(" ${resources.getString(R.string.percent)} ")
                                initialEquation.value = inputtedValue.toString()
                            }
                        } else { Snackbar.make(requireView(), "Error. Please tap AC or equals button first", Snackbar.LENGTH_SHORT).show() }
                    }
                }

                btnReturns.setOnClickListener {
                    try {
                        initialEquation.value = when (inputtedValue.length) {
                            1 -> ""
                            else -> {
                                val tempStringStorage: String = initialEquation.value.toString()
                                val endIndex = if (tempStringStorage.endsWith(' ')) tempStringStorage.length - 3 else tempStringStorage.length - 1
                                tempStringStorage.substring(0, endIndex)
                            }
                        }
                        inputtedValue.setLength(0)
                        inputtedValue.append(initialEquation.value)
                        initialEquation.value = inputtedValue.toString()
                    } catch (ignored: Exception) { Log.i("MyTag", ignored.toString()) }
                }

                btnAC.setOnClickListener {
                    if (sharedPreferences.getString("Memory", "0") == "0" || sharedPreferences.getString("Memory", "0") == "") {
                        configureMemoryTextView(false, tvMR)
                        configureMemoryTextView(false, tvMC)
                        configureMemoryTextView(false, tvMPlus)
                        configureMemoryTextView(false, tvMMinus)
                    } else {
                        configureMemoryTextView(true, tvMR)
                        configureMemoryTextView(true, tvMC)
                        configureMemoryTextView(true, tvMPlus)
                        configureMemoryTextView(true, tvMMinus)
                    }
                    tvMemoryMode.text = "Standard  |  ${sharedPreferences.getString("Memory", "0")}"
                    pointUsageIdentifier = true
                    isPercentUsed.value = false
                    isMemoryButtonSelected.value = false
                    inputtedValue.setLength(0)
                    initialEquation.value = inputtedValue.toString()
                    answer.setLength(0)
                    answerFromVM.value = answer.toString()
                    tvSolution.text = "0"
                    tvNegateCounter.text = "0"
                    negateCount.value = 0
                }

                btnEquals.setOnClickListener {
                    isEqualsSelected.value = true
                    isMemoryButtonSelected.value = false

                    try {
                        if (tvSolution.text.isNullOrEmpty() ||
                            isInvalidOperator(inputtedValue.toString()) ||
                            tvSolution.text.last() == resources.getString(R.string.dot).toCharArray()[0]
                        ) {
                            Snackbar.make(requireView(), "Error. Invalid input", Snackbar.LENGTH_SHORT).show()
                        } else {
                            val scriptEngineManager = ScriptEngineManager()
                            val scriptEngine = scriptEngineManager.getEngineByName("rhino")
                            homeCurrentHistory.setLength(0)
                            homeCurrentHistory.append(tvSolution.text)
                            recentHistory.value = homeCurrentHistory.toString()

                            val formattedEquation = initialEquation.value.toString()
                                .replace(" ", "")
                                .replace(resources.getString(R.string.minus), "-")
                                .replace(resources.getString(R.string.multiply), "*")
                                .replace(resources.getString(R.string.divide), "/")
                                .replace(",", "")

                            val result = if (!formattedEquation.contains("%")) {
                                decimalFormat.format(scriptEngine.eval(formattedEquation) as Double)
                            } else {
                                try {
                                    val tempStorage: List<String> = formattedEquation.split("%")
                                    var answer = 0.0
                                    for (i in 0 until tempStorage.size - 1) {
                                        val firstNum: Double = tempStorage[i].toDouble()
                                        val secondNum: Double = tempStorage[i + 1].toDouble()
                                        answer = (firstNum * 0.01) * secondNum
                                    }
                                    decimalFormat.format(answer)
                                } catch (ignored: Exception) { "" }
                            }
                            answerFromVM.value = result
                            if (formattedEquation.isNotEmpty() && result.isNotEmpty()) { dbViewModel.insertEntityFromVM(Entity(0, "$formattedEquation = $result")) }
                        }
                    } catch (ignored: Exception) {}
                }

                btnSign.setOnClickListener {
                    val currentAnswer = answerFromVM.value ?: ""
                    if (currentAnswer.isNotEmpty()) {
                        negateCount.value = negateCount.value?.plus(1)
                        val sign = if (negateCount.value!! % 2 == 1) "-" else "+"
                        val newAnswer = if (sign == "-") "-${currentAnswer.replace("-", "").replace("+", "")}" else currentAnswer.replace("-", "").replace("+", "")

                        tvNegateCounter.text = "${negateCount.value}  |  $sign"
                        answer.setLength(0)
                        answerFromVM.value = newAnswer
                    }
                }
            }
//            ivHistory?.setOnClickListener { HomeHistory.newInstance(30).show(childFragmentManager, "HomeBottomSheetDialog") }
        }
    }

    private fun configureNumKeyLogger() {
        binding.apply {
            val numButtons = listOf(
                btn0 to R.string.zero,
                btn1 to R.string.one,
                btn2 to R.string.two,
                btn3 to R.string.three,
                btn4 to R.string.four,
                btn5 to R.string.five,
                btn6 to R.string.six,
                btn7 to R.string.seven,
                btn8 to R.string.eight,
                btn9 to R.string.nine
            )

            for ((button, stringResource) in numButtons) {
                button.setOnClickListener {
                    if (homeViewModel.isEqualsSelected.value == true && homeViewModel.isMemoryButtonSelected.value != true) {
                        inputtedValue.setLength(0)
                        homeViewModel.initialEquation.value = inputtedValue.toString()
                        homeViewModel.isEqualsSelected.value = false
                    }

                    inputtedValue.append(resources.getString(stringResource))
                    homeViewModel.initialEquation.value = inputtedValue.toString()
                }
            }

            val operatorButtons = listOf(
                btnPlus to R.string.plus,
                btnMinus to R.string.minus,
                btnMultiply to R.string.multiply,
                btnDivide to R.string.divide
            )

            for ((button, stringResource) in operatorButtons) {
                button.setOnClickListener {
                    if (homeViewModel.isEqualsSelected.value == true && stringResource != R.string.dot && homeViewModel.isMemoryButtonSelected.value != true) {
                        inputtedValue.setLength(0)
                        homeViewModel.initialEquation.value = ""
                        inputtedValue.append(homeViewModel.answerFromVM.value.toString())
                        homeViewModel.answerFromVM.value = ""
                        homeViewModel.isEqualsSelected.value = false
                    }

                    val stringFromViewModel: String = homeViewModel.initialEquation.value.toString()
                    try {
                        if (!isInvalidOperator(stringFromViewModel)) {
                            inputtedValue.append(" " + resources.getString(stringResource) + " ")
                            homeViewModel.initialEquation.value = inputtedValue.toString()
                            pointUsageIdentifier = true
                        } else { Snackbar.make(requireView(), "Error. Invalid action", Snackbar.LENGTH_SHORT).show() }
                    } catch (e: Exception) {
                        inputtedValue.append(" " + resources.getString(stringResource) + " ")
                        homeViewModel.initialEquation.value = inputtedValue.toString()
                        pointUsageIdentifier = true
                    }
                }
            }
        }
    }

    private fun isInvalidOperator(string: String): Boolean {
        val operatorList: List<String> = listOf(
            resources.getString(R.string.plus),
            resources.getString(R.string.minus),
            resources.getString(R.string.multiply),
            resources.getString(R.string.divide),
            resources.getString(R.string.dot)
        )
        return operatorList.contains(string.last().toString())
    }

    override fun onResume() {
        super.onResume()
        binding.apply {
//            tvHistory?.text = sharedPreferences.getString("History_Key_SP", "")
            tvSolution.text = sharedPreferences.getString("Solution_Key_SP", "")
            tvAnswer.text = "= ${sharedPreferences.getString("Answer_Key2_SP", "")}"
            editor.putString("Memory", "0")
            editor.apply()
        }
    }

    override fun onPause() {
        super.onPause()
        binding.apply {
            editor.putString("History_Key_SP", homeViewModel.recentHistory.value)
            editor.putString("Solution_Key_SP", homeViewModel.initialEquation.value)
            editor.putString("Answer_Key2_SP", homeViewModel.answerFromVM.value ?: "= ")
            editor.apply()
        }
    }
}