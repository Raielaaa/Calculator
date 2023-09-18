package com.example.calculatorresponsivetest4.ui.converter

import android.net.Uri

object ConverterItems {
    val converterItemList: List<ConverterItemModel> = listOf(
        ConverterItemModel("Length", Uri.parse("android.resource://com.example.calculatorresponsivetest4/drawable/converter_length")),
        ConverterItemModel("Mass", Uri.parse("android.resource://com.example.calculatorresponsivetest4/drawable/converter_mass")),
        ConverterItemModel("Speed", Uri.parse("android.resource://com.example.calculatorresponsivetest4/drawable/converter_speed")),
        ConverterItemModel("Temperature", Uri.parse("android.resource://com.example.calculatorresponsivetest4/drawable/converter_temperature")),
        ConverterItemModel("Angle", Uri.parse("android.resource://com.example.calculatorresponsivetest4/drawable/converter_angle")),
        ConverterItemModel("Pressure", Uri.parse("android.resource://com.example.calculatorresponsivetest4/drawable/converter_pressure")),
        ConverterItemModel("Energy", Uri.parse("android.resource://com.example.calculatorresponsivetest4/drawable/converter_energy"))
    )
}