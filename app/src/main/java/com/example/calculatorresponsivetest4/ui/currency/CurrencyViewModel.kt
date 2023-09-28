package com.example.calculatorresponsivetest4.ui.currency

import android.opengl.Visibility
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import com.example.calculatorresponsivetest4.api.ApiResponse
import com.example.calculatorresponsivetest4.api.CurrencyApi
import kotlinx.coroutines.launch
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class CurrencyViewModel : ViewModel() {
    val convertedRate: MutableLiveData<Double> by lazy {
        MutableLiveData<Double>()
    }
    val dateFromApiCall: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }
    private lateinit var result: Response<ApiResponse>

    fun getConvertedCurrency(from: String, to: String, amount: Double, fragment: CurrencyFragment, progressBar: ProgressBar, tvCurrencyDate: TextView) {
        tvCurrencyDate.visibility = View.INVISIBLE
        progressBar.visibility = View.VISIBLE
        fragment.viewLifecycleOwner.lifecycleScope.launch {
            try {
                result = Retrofit.Builder()
                    .baseUrl("https://api.getgeoapi.com/v2/currency/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(CurrencyApi::class.java)
                    .convertCurrency(
                        "a86dcbbb936e118b1213616b268b35ab3c289387",
                        from.substring(0, 3),
                        to.substring(0, 3),
                        amount
                    )

                if (result.isSuccessful) {
                    tvCurrencyDate.visibility = View.VISIBLE
                    progressBar.visibility = View.INVISIBLE
                    convertedRate.value = result.body()!!.rates[to.substring(0, 3)]?.rate_for_amount?.toDouble() ?: 0.0
                    dateFromApiCall.value = result.body()!!.updated_date
                } else {
                    Log.e("MyTag", "Failed to fetch data: ${result.message()}")
                }
            } catch (e: Exception) {
                Toast.makeText(fragment.requireContext(), "Oops! an error occurred.", Toast.LENGTH_LONG).show()
            }
        }
    }
}