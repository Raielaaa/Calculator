package com.example.calculatorresponsivetest4.api

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface CurrencyApi {
    @GET("convert")
    suspend fun convertCurrency(
        @Query("api_key")
        access_key: String,
        @Query("from")
        from: String,
        @Query("to")
        to: String,
        @Query("amount")
        amount: Double
    ) : Response<ApiResponse>
}