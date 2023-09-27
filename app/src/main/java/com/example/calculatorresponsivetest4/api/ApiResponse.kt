package com.example.calculatorresponsivetest4.api

data class ApiResponse(
    val amount: Double,
    val base_currency_code: String,
    val base_currency_name: String,
    val rates: Map<String, Rates>,
    val status: String,
    val updated_date: String
)