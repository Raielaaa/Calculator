package com.example.calculatorresponsivetest4.ui.converter

import java.text.DecimalFormat
import android.content.res.Resources
import android.content.res.Resources.NotFoundException
import android.util.Log
import com.example.calculatorresponsivetest4.R

object UnitConverter {


    // The numerical value at the start represents the rank of the unit in regards to its real world size
    const val nanometer: String = "0_nanometers"
    const val micrometer: String = "1_microns"
    const val millimeter: String = "2_millimeters"
    const val centimeter: String = "3_centimeters"
    const val inch: String = "4_inches"
    const val foot: String = "5_feet"
    const val yard: String = "6_yards"
    const val meter: String = "7_meters"
    const val kilometer: String = "8_kilometers"
    const val mile: String = "9_miles"

//    private val decimalFormat: DecimalFormat = DecimalFormat(Resources.getSystem().getString(R.string.NumberFormat))
    private val decimalFormat: DecimalFormat = DecimalFormat("#,###,###,###,###,###,###,###,###,###,###.#######")

    // Unit converter - Length
    private fun convert(numberInput: Double, fromUnit: String, toUnit: String): String {
        val conversionFactor: Double = getConversionFactor(fromUnit, toUnit)
        Log.i("MyTag", "Number input: $numberInput\nFrom unit: $fromUnit\nTo unit: $toUnit\nConversion factor: $conversionFactor")
        return if (fromUnit[0].code < toUnit[0].code) decimalFormat.format(numberInput / conversionFactor) else decimalFormat.format(numberInput * conversionFactor)
    }

    private fun getConversionFactor(fromUnit: String, toUnit: String): Double {
        Log.i("MyTag", "2-$fromUnit\nTo unit: $toUnit")
        return when (fromUnit) {
            "0_nanometers" -> when (toUnit) {
                "0_nanometers" -> 1.0
                "1_microns" -> 1000.0
                "2_millimeters" -> 10000000.0
                "3_centimeters" -> 100000000.0
                "7_meters" -> 1000000000.0
                "8_kilometers" -> 1000000000000.0
                "4_inches" -> 25400000.0
                "5_feet" -> 30480000.0
                "6_yards" -> 91440000.0
                "9_miles" -> 16093440000.0
                else -> throw NotFoundException()
            }
            "1_microns" -> when (toUnit) {
                "0_nanometers" -> 1000.0 // m
                "1_microns" -> 1.0
                "2_millimeters" -> 1000.0
                "3_centimeters" -> 10000.0
                "7_meters" -> 1000000.0
                "8_kilometers" -> 1000000000.0
                "4_inches" -> 25400.0
                "5_feet" -> 304800.0
                "6_yards" -> 914400.0
                "9_miles" -> 1609344000.0
                else -> throw NotFoundException()
            }
            "2_millimeters" -> when (toUnit) {
                "0_nanometers" -> 1000000.0 // m
                "1_microns" -> 1000.0 // m
                "2_millimeters" -> 1.0
                "3_centimeters" -> 10.0
                "7_meters" -> 1000.0
                "8_kilometers" -> 1000000.0
                "4_inches" -> 25.4
                "5_feet" -> 304.8
                "6_yards" -> 914.4
                "9_miles" -> 1609344.0
                else -> throw NotFoundException()
            }
            "3_centimeters" -> when (toUnit) {
                "0_nanometers" -> 10000000.0 // m
                "1_microns" -> 10000.0 // m
                "2_millimeters" -> 10.0 // m
                "3_centimeters" -> 1.0
                "7_meters" -> 100.0
                "8_kilometers" -> 100000.0
                "4_inches" -> 2.54
                "5_feet" -> 30.48
                "6_yards" -> 91.44
                "9_miles" -> 160900.0
                else -> throw NotFoundException()
            }
            "7_meters" -> when (toUnit) {
                "0_nanometers" -> 1000000000.0 // m
                "1_microns" -> 1000000.0 // m
                "2_millimeters" -> 1000.0 // m
                "3_centimeters" -> 100.0 // m
                "7_meters" -> 1.0
                "8_kilometers" -> 1000.0
                "4_inches" -> 39.37 // m
                "5_feet" -> 3.281 // m
                "6_yards" -> 1.094 // m
                "9_miles" -> 1609.0
                else -> throw NotFoundException()
            }
            "8_kilometers" -> when (toUnit) {
                "0_nanometers" -> 1000000000000.0 // m
                "1_microns" -> 1000000000.0 // m
                "2_millimeters" -> 1000000.0 // m
                "3_centimeters" -> 100000.0 // m
                "7_meters" -> 1000.0 // m
                "8_kilometers" -> 0.0
                "4_inches" -> 39370.0 // m
                "5_feet" -> 3281.0 // m
                "6_yards" -> 1094.0 // m
                "9_miles" -> 1.609
                else -> throw NotFoundException()
            }
            "4_inches" -> when (toUnit) {
                "0_nanometers" -> 25400000.0 // m
                "1_microns" -> 25400.0 // m
                "2_millimeters" -> 25.4 // m
                "3_centimeters" -> 2.54 // m
                "7_meters" -> 39.37
                "8_kilometers" -> 39370.0
                "4_inches" -> 1.0
                "5_feet" -> 12.0
                "6_yards" -> 36.0
                "9_miles" -> 63360.0
                else -> throw NotFoundException()
            }
            "5_feet" -> when (toUnit) {
                "0_nanometers" -> 304800000.0 // m
                "1_microns" -> 304800.0 // m
                "2_millimeters" -> 304.8 // m
                "3_centimeters" -> 30.48 // m
                "7_meters" -> 3.281
                "8_kilometers" -> 3281.0
                "4_inches" -> 12.0 // m
                "5_feet" -> 1.0
                "6_yards" -> 3.0
                "9_miles" -> 5280.0
                else -> throw NotFoundException()
            }
            "6_yards" -> when (toUnit) {
                "0_nanometers" -> 914400000.0 // m
                "1_microns" -> 914400.0 // m
                "2_millimeters" -> 914.4 // m
                "3_centimeters" -> 91.44 // m
                "7_meters" -> 1.094
                "8_kilometers" -> 1094.0
                "4_inches" -> 36.0 // m
                "5_feet" -> 3.0 // m
                "6_yards" -> 1.0
                "9_miles" -> 1760.0
                else -> throw NotFoundException()
            }
            "9_miles" -> when (toUnit) {
                "0_nanometers" -> 1609000000000.0 // m
                "1_microns" -> 1609000000.0 // m
                "2_millimeters" -> 1609000.0 // m
                "3_centimeters" -> 160900.0 // m
                "7_meters" -> 1609.0 // m
                "8_kilometers" -> 1.609 // m
                "4_inches" -> 63360.0 // m
                "5_feet" -> 5280.0 // m
                "6_yards" -> 1760.0 // m
                "9_miles" -> 1.0
                else -> throw NotFoundException()
            }
            else -> throw NotFoundException()
        }
    }

    fun lengthFormula(updatedNumInput: Double, pairValueUnitFrom: Pair<String, String>, pairValueUnitTo: Pair<String, String>): String {
        return when (pairValueUnitFrom.second) {
            nanometer -> lengthFormulaFunctionHelper(updatedNumInput, pairValueUnitFrom, pairValueUnitTo)
            micrometer -> lengthFormulaFunctionHelper(updatedNumInput, pairValueUnitFrom, pairValueUnitTo)
            millimeter -> lengthFormulaFunctionHelper(updatedNumInput, pairValueUnitFrom, pairValueUnitTo)
            centimeter -> lengthFormulaFunctionHelper(updatedNumInput, pairValueUnitFrom, pairValueUnitTo)
            meter -> lengthFormulaFunctionHelper(updatedNumInput, pairValueUnitFrom, pairValueUnitTo)
            kilometer -> lengthFormulaFunctionHelper(updatedNumInput, pairValueUnitFrom, pairValueUnitTo)
            inch -> lengthFormulaFunctionHelper(updatedNumInput, pairValueUnitFrom, pairValueUnitTo)
            foot -> lengthFormulaFunctionHelper(updatedNumInput, pairValueUnitFrom, pairValueUnitTo)
            yard -> lengthFormulaFunctionHelper(updatedNumInput, pairValueUnitFrom, pairValueUnitTo)
            mile -> lengthFormulaFunctionHelper(updatedNumInput, pairValueUnitFrom, pairValueUnitTo)
            else -> throw NotFoundException()
        }
    }

    private fun lengthFormulaFunctionHelper(updatedNumInput: Double, pairValueUnitFrom: Pair<String, String>, pairValueUnitTo: Pair<String, String>): String {
        return when (pairValueUnitTo.second) {
            nanometer -> convert(updatedNumInput, pairValueUnitFrom.first, nanometer)
            micrometer -> convert(updatedNumInput, pairValueUnitFrom.first, micrometer)
            millimeter -> convert(updatedNumInput, pairValueUnitFrom.first, millimeter)
            centimeter ->  convert(updatedNumInput, pairValueUnitFrom.first, centimeter)
            meter -> convert(updatedNumInput, pairValueUnitFrom.first, meter)
            kilometer -> convert(updatedNumInput, pairValueUnitFrom.first, kilometer)
            inch -> convert(updatedNumInput, pairValueUnitFrom.first, inch)
            foot -> convert(updatedNumInput, pairValueUnitFrom.first, foot)
            yard -> convert(updatedNumInput, pairValueUnitFrom.first, yard)
            mile -> convert(updatedNumInput, pairValueUnitFrom.first, mile)
            else -> throw NotFoundException()
        }
    }
    // Unit converter -
}