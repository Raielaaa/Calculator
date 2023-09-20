package com.example.calculatorresponsivetest4.ui.converter

import java.text.DecimalFormat
import android.content.res.Resources.NotFoundException

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

    // ----------------------------------------Unit converter - LENGTH------------------------------
    private fun convert(numberInput: Double, fromUnit: String, toUnit: String): String {
        val conversionFactor: Double = getConversionFactor(fromUnit, toUnit)
        return if (fromUnit[0].code < toUnit[0].code) decimalFormat.format(numberInput / conversionFactor) else decimalFormat.format(numberInput * conversionFactor)
    }

    private fun getConversionFactor(fromUnit: String, toUnit: String): Double {
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

    fun getLengthConversionAnswer(updatedNumInput: Double, pairValueUnitFrom: Pair<String, String>, pairValueUnitTo: Pair<String, String>): String {
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

    // ----------------------------------------Unit converter - ENERGY------------------------------
    fun getEnergyConversionAnswer(updatedNumInput: Double, selectedItemFrom: Any?, selectedItemTo: Any?): String {
        val unit_ev = "(eV) Electron volts"
        val unit_j = "(J) Joules"
        val unit_kj = "(kJ) Kilojoules"
        val unit_cal = "(cal) Thermal calories"
        val unit_kcal = "(kcal) Food calories"
        val unit_ftlb = "(ft⋅lb) Foot-pounds"
        val unit_Btu = "(Btu) British thermal units"
        val unit_kwh = "(kW⋅h) Kilowatt-hours"

        var answer: String? = null

        when (selectedItemFrom.toString()) {
            unit_ev -> when (selectedItemTo) {
                unit_ev -> answer = "${updatedNumInput * 1}"
                unit_j -> answer = "${updatedNumInput * 1.60219e-19}"
                unit_kj -> answer = "${updatedNumInput * 1.60219e-22}"
                unit_cal -> answer = "${updatedNumInput * 3.8293e-20}"
                unit_kcal -> answer = "${updatedNumInput * 3.8293e-23}"
                unit_ftlb -> answer = "${updatedNumInput * 1.1817e-19}"
                unit_Btu -> answer = "${updatedNumInput * 9.4782e-24}"
                unit_kwh -> answer = "${updatedNumInput * 4.4505e-26}"
            }
            unit_j -> when (selectedItemTo) {
                unit_ev -> answer = "${updatedNumInput * 6.242e+12}"
                unit_j -> answer = "${updatedNumInput * 1}"
                unit_kj -> answer = "${updatedNumInput * 0.001}"
                unit_cal -> answer = "${updatedNumInput * 0.239006}"
                unit_kcal -> answer = "${updatedNumInput * 0.000239006}"
                unit_ftlb -> answer = "${updatedNumInput * 0.737562}"
                unit_Btu -> answer = "${updatedNumInput * 0.000947817}"
                unit_kwh -> answer = "${updatedNumInput * 2.77778e-07}"
            }
            unit_kj -> when (selectedItemTo) {
                unit_ev -> answer = "${updatedNumInput * 6.242e+18}"
                unit_j -> answer = "${updatedNumInput * 1000.0}"
                unit_kj -> answer = "${updatedNumInput * 1}"
                unit_cal -> answer = "${updatedNumInput * 239.006}"
                unit_kcal -> answer = "${updatedNumInput * 0.239006}"
                unit_ftlb -> answer = "${updatedNumInput * 737.562}"
                unit_Btu -> answer = "${updatedNumInput * 0.947817}"
                unit_kwh -> answer = "${updatedNumInput * 0.000277778}"
            }
            unit_cal -> when (selectedItemTo) {
                unit_ev -> answer = "${updatedNumInput * 2.611e+19}"
                unit_j -> answer = "${updatedNumInput * 4.184}"
                unit_kj -> answer = "${updatedNumInput * 0.004184}"
                unit_cal -> answer = "${updatedNumInput * 1}"
                unit_kcal -> answer = "${updatedNumInput * 0.001}"
                unit_ftlb -> answer = "${updatedNumInput * 0.0113558}"
                unit_Btu -> answer = "${updatedNumInput * 0.00396832}"
                unit_kwh -> answer = "${updatedNumInput * 1.163e-07}"
            }
            unit_kcal -> when (selectedItemTo) {
                unit_ev -> answer = "${updatedNumInput * 2.613e+19}"
                unit_j -> answer = "${updatedNumInput * 4184.0}"
                unit_kj -> answer = "${updatedNumInput * 4.184}"
                unit_cal -> answer = "${updatedNumInput * 1000.0}"
                unit_kcal -> answer = "${updatedNumInput * 1}"
                unit_ftlb -> answer = "${updatedNumInput * 3088.02}"
                unit_Btu -> answer = "${updatedNumInput * 3.96567}"
                unit_kwh -> answer = "${updatedNumInput * 1.16278e-06}"
            }
            unit_ftlb -> when (selectedItemTo) {
                unit_ev -> answer = "${updatedNumInput * 8.46235e+18}"
                unit_j -> answer = "${updatedNumInput * 1.35582}"
                unit_kj -> answer = "${updatedNumInput * 0.00135582}"
                unit_cal -> answer = "${updatedNumInput * 323.832}"
                unit_kcal -> answer = "${updatedNumInput * 0.323832}"
                unit_ftlb -> answer = "${updatedNumInput * 1}"
                unit_Btu -> answer = "${updatedNumInput * 0.00128507}"
                unit_kwh -> answer = "${updatedNumInput * 3.725e-07}"
            }
            unit_Btu -> when (selectedItemTo) {
                unit_ev -> answer = "${updatedNumInput * 2.685e+16}"
                unit_j -> answer = "${updatedNumInput * 1055.06}"
                unit_kj -> answer = "${updatedNumInput * 1.05506}"
                unit_cal -> answer = "${updatedNumInput * 251.997}"
                unit_kcal -> answer = "${updatedNumInput * 0.251997}"
                unit_ftlb -> answer = "${updatedNumInput * 778.169}"
                unit_Btu -> answer = "${updatedNumInput * 1}"
                unit_kwh -> answer = "${updatedNumInput * 2.93071e-07}"
            }
            unit_kwh -> when (selectedItemTo) {
                unit_ev -> answer = "${updatedNumInput * 3.6e+6}"
                unit_j -> answer = "${updatedNumInput * 3.6e+6}"
                unit_kj -> answer = "${updatedNumInput * 3.6e+3}"
                unit_cal -> answer = "${updatedNumInput * 8.598e+5}"
                unit_kcal -> answer = "${updatedNumInput * 8.598e+2}"
                unit_ftlb -> answer = "${updatedNumInput * 2.655e+6}"
                unit_Btu -> answer = "${updatedNumInput * 3.412e+3}"
                unit_kwh -> answer = "${updatedNumInput * 1}"
            }
        }

        return answer ?: "Invalid conversion"
    }

    // --------------------------------------Unit converter - PRESSURE------------------------------
    fun getPressureConversionAnswer(updatedNumInput: Double, selectedItemFrom: Any?, selectedItemTo: Any?): String {
        val unit_atm = "(atm) Atmospheres"
        val unit_bar = "(bar) Bars"
        val unit_kpa = "(kPa) Kilopascals"
        val unit_mmhg = "(mmHg) Millimeters of Mercury"
        val unit_pa = "(Pa) Pascals"
        val unit_psi = "(psi) Pounds per Square Inch"

        var answer: String? = null

        when (selectedItemFrom.toString()) {
            unit_atm -> when (selectedItemTo) {
                unit_atm -> answer = "${updatedNumInput * 1}"
                unit_bar -> answer = "${updatedNumInput * 1.01325}"
                unit_kpa -> answer = "${updatedNumInput * 101.325}"
                unit_mmhg -> answer = "${updatedNumInput * 760}"
                unit_pa -> answer = "${updatedNumInput * 101325}"
                unit_psi -> answer = "${updatedNumInput * 14.696}"
            }
            unit_bar -> when (selectedItemTo) {
                unit_atm -> answer = "${updatedNumInput * 0.98692}"
                unit_bar -> answer = "${updatedNumInput * 1}"
                unit_kpa -> answer = "${updatedNumInput * 100}"
                unit_mmhg -> answer = "${updatedNumInput * 750.062}"
                unit_pa -> answer = "${updatedNumInput * 100000}"
                unit_psi -> answer = "${updatedNumInput * 14.5038}"
            }
            unit_kpa -> when (selectedItemTo) {
                unit_atm -> answer = "${updatedNumInput * 0.00986923}"
                unit_bar -> answer = "${updatedNumInput * 0.01}"
                unit_kpa -> answer = "${updatedNumInput * 1}"
                unit_mmhg -> answer = "${updatedNumInput * 7.50062}"
                unit_pa -> answer = "${updatedNumInput * 1000}"
                unit_psi -> answer = "${updatedNumInput * 0.145038}"
            }
            unit_mmhg -> when (selectedItemTo) {
                unit_atm -> answer = "${updatedNumInput * 0.00131579}"
                unit_bar -> answer = "${updatedNumInput * 0.00133322}"
                unit_kpa -> answer = "${updatedNumInput * 0.133322}"
                unit_mmhg -> answer = "${updatedNumInput * 1}"
                unit_pa -> answer = "${updatedNumInput * 133.322}"
                unit_psi -> answer = "${updatedNumInput * 0.0193368}"
            }
            unit_pa -> when (selectedItemTo) {
                unit_atm -> answer = "${updatedNumInput * 9.8692e-06}"
                unit_bar -> answer = "${updatedNumInput * 1e-05}"
                unit_kpa -> answer = "${updatedNumInput * 0.001}"
                unit_mmhg -> answer = "${updatedNumInput * 0.00750062}"
                unit_pa -> answer = "${updatedNumInput * 1}"
                unit_psi -> answer = "${updatedNumInput * 0.000145038}"
            }
            unit_psi -> when (selectedItemTo) {
                unit_atm -> answer = "${updatedNumInput * 0.0680459}"
                unit_bar -> answer = "${updatedNumInput * 0.0689476}"
                unit_kpa -> answer = "${updatedNumInput * 6.89476}"
                unit_mmhg -> answer = "${updatedNumInput * 51.7149}"
                unit_pa -> answer = "${updatedNumInput * 6894.76}"
                unit_psi -> answer = "${updatedNumInput * 1}"
            }
        }

        return answer ?: "Invalid conversion"
    }

    // ----------------------------------------Unit converter - SPEED----------_--------------------
    fun getSpeedConversionAnswer(updatedNumInput: Double, selectedItemFrom: Any?, selectedItemTo: Any?): String {
        val unit_cms = "(cm/s) Centimeters per second"
        val unit_ms = "(m/s) Meters per second"
        val unit_kmh = "(km/h) Kilometers per hour"
        val unit_fts = "(ft/s) Feet per second"
        val unit_mih = "(mi/h) Miles per hour"
        val unit_kn = "(kn) Knots"
        val unit_m = "(M) Mach"

        var answer: String? = null

        when (selectedItemFrom.toString()) {
            unit_cms -> when (selectedItemTo) {
                unit_cms -> answer = "${updatedNumInput * 1}"
                unit_ms -> answer = "${updatedNumInput * 0.01}"
                unit_kmh -> answer = "${updatedNumInput * 0.036}"
                unit_fts -> answer = "${updatedNumInput * 0.0328084}"
                unit_mih -> answer = "${updatedNumInput * 0.0223694}"
                unit_kn -> answer = "${updatedNumInput * 0.0194385}"
                unit_m -> answer = "${updatedNumInput * 2.941e-5}"
            }
            unit_ms -> when (selectedItemTo) {
                unit_cms -> answer = "${updatedNumInput * 100}"
                unit_ms -> answer = "${updatedNumInput * 1}"
                unit_kmh -> answer = "${updatedNumInput * 3.6}"
                unit_fts -> answer = "${updatedNumInput * 3.28084}"
                unit_mih -> answer = "${updatedNumInput * 2.23694}"
                unit_kn -> answer = "${updatedNumInput * 1.94385}"
                unit_m -> answer = "${updatedNumInput * 0.002941}"
            }
            unit_kmh -> when (selectedItemTo) {
                unit_cms -> answer = "${updatedNumInput * 27.7778}"
                unit_ms -> answer = "${updatedNumInput * 0.277778}"
                unit_kmh -> answer = "${updatedNumInput * 1}"
                unit_fts -> answer = "${updatedNumInput * 0.911344}"
                unit_mih -> answer = "${updatedNumInput * 0.621371}"
                unit_kn -> answer = "${updatedNumInput * 0.539957}"
                unit_m -> answer = "${updatedNumInput * 0.000816}"
            }
            unit_fts -> when (selectedItemTo) {
                unit_cms -> answer = "${updatedNumInput * 30.48}"
                unit_ms -> answer = "${updatedNumInput * 0.3048}"
                unit_kmh -> answer = "${updatedNumInput * 1.09728}"
                unit_fts -> answer = "${updatedNumInput * 1}"
                unit_mih -> answer = "${updatedNumInput * 0.681818}"
                unit_kn -> answer = "${updatedNumInput * 0.592484}"
                unit_m -> answer = "${updatedNumInput * 0.000895}"
            }
            unit_mih -> when (selectedItemTo) {
                unit_cms -> answer = "${updatedNumInput * 44.704}"
                unit_ms -> answer = "${updatedNumInput * 0.44704}"
                unit_kmh -> answer = "${updatedNumInput * 1.60934}"
                unit_fts -> answer = "${updatedNumInput * 1.46667}"
                unit_mih -> answer = "${updatedNumInput * 1}"
                unit_kn -> answer = "${updatedNumInput * 0.868976}"
                unit_m -> answer = "${updatedNumInput * 0.001313}"
            }
            unit_kn -> when (selectedItemTo) {
                unit_cms -> answer = "${updatedNumInput * 51.444}"
                unit_ms -> answer = "${updatedNumInput * 0.51444}"
                unit_kmh -> answer = "${updatedNumInput * 1.852}"
                unit_fts -> answer = "${updatedNumInput * 1.68781}"
                unit_mih -> answer = "${updatedNumInput * 1.15078}"
                unit_kn -> answer = "${updatedNumInput * 1}"
                unit_m -> answer = "${updatedNumInput * 0.001514}"
            }
            unit_m -> when (selectedItemTo) {
                unit_cms -> answer = "${updatedNumInput * 34029.9}"
                unit_ms -> answer = "${updatedNumInput * 340.29}"
                unit_kmh -> answer = "${updatedNumInput * 1234.8}"
                unit_fts -> answer = "${updatedNumInput * 1125.33}"
                unit_mih -> answer = "${updatedNumInput * 767.262}"
                unit_kn -> answer = "${updatedNumInput * 666.739}"
                unit_m -> answer = "${updatedNumInput * 1}"
            }
        }

        return answer ?: "Invalid conversion"
    }

    // ----------------------------------------Unit converter - ANGLE---------_---------------------
    fun getAngleConversionAnswer(updatedNumInput: Double, selectedItemFrom: Any?, selectedItemTo: Any?): String {
        val unit_deg = "(deg) Degrees"
        val unit_rad = "(rad) Radians"
        val unit_gon = "(gon) Gradians"

        var answer: String? = null

        when (selectedItemFrom.toString()) {
            unit_deg -> when (selectedItemTo) {
                unit_deg -> answer = "${updatedNumInput * 1}"
                unit_rad -> answer = "${updatedNumInput * (Math.PI / 180)}"
                unit_gon -> answer = "${updatedNumInput * 1.11111}"
            }
            unit_rad -> when (selectedItemTo) {
                unit_deg -> answer = "${updatedNumInput * (180 / Math.PI)}"
                unit_rad -> answer = "${updatedNumInput * 1}"
                unit_gon -> answer = "${updatedNumInput * (200 / Math.PI)}"
            }
            unit_gon -> when (selectedItemTo) {
                unit_deg -> answer = "${updatedNumInput * 0.9}"
                unit_rad -> answer = "${updatedNumInput * (Math.PI / 200)}"
                unit_gon -> answer = "${updatedNumInput * 1}"
            }
        }

        return answer ?: "Invalid conversion"
    }

    // ----------------------------------------Unit converter - MASS---------------------__---------
    fun getMassConversionAnswer(updatedNumInput: Double, selectedItemFrom: Any?, selectedItemTo: Any?): String {
        val unit_mg = "(mg) Milligrams"
        val unit_cg = "(cg) Centigrams"
        val unit_dg = "(dg) Decigrams"
        val unit_g = "(g) Grams"
        val unit_dag = "(dag) Decagrams"
        val unit_kg = "(kg) Kilograms"
        val unit_t = "(t) Metric tonnes"
        val unit_oz = "(oz) Ounces"
        val unit_lb = "(lb) Pounds"

        var answer: String? = null

        when (selectedItemFrom.toString()) {
            unit_mg -> when(selectedItemTo) {
                unit_mg -> answer = "${updatedNumInput * 1}"
                unit_cg -> answer = "${updatedNumInput * 10}"
                unit_dg -> answer = "${updatedNumInput * 100}"
                unit_g -> answer = "${updatedNumInput * 1000}"
                unit_dag -> answer = "${updatedNumInput * 10000}"
                unit_kg -> answer = "${updatedNumInput * 1000000}"
                unit_t -> answer = "${updatedNumInput * 1000000000}"
                unit_oz -> answer = "${updatedNumInput * 0.03527396}"
                unit_lb -> answer = "${updatedNumInput * 0.00220462}"
            }
            unit_cg -> when(selectedItemTo) {
                unit_mg -> answer = "${updatedNumInput * 0.1}"
                unit_cg -> answer = "${updatedNumInput * 1}"
                unit_dg -> answer = "${updatedNumInput * 10}"
                unit_g -> answer = "${updatedNumInput * 100}"
                unit_dag -> answer = "${updatedNumInput * 1000}"
                unit_kg -> answer = "${updatedNumInput * 100000}"
                unit_t -> answer = "${updatedNumInput * 100000000}"
                unit_oz -> answer = "${updatedNumInput * 0.003527396}"
                unit_lb -> answer = "${updatedNumInput * 0.000220462}"
            }
            unit_dg -> when(selectedItemTo) {
                unit_mg -> answer = "${updatedNumInput * 0.01}"
                unit_cg -> answer = "${updatedNumInput * 0.1}"
                unit_dg -> answer = "${updatedNumInput * 1}"
                unit_g -> answer = "${updatedNumInput * 10}"
                unit_dag -> answer = "${updatedNumInput * 100}"
                unit_kg -> answer = "${updatedNumInput * 10000}"
                unit_t -> answer = "${updatedNumInput * 10000000}"
                unit_oz -> answer = "${updatedNumInput * 0.0003527396}"
                unit_lb -> answer = "${updatedNumInput * 0.0000220462}"
            }
            unit_g -> when(selectedItemTo) {
                unit_mg -> answer = "${updatedNumInput * 0.001}"
                unit_cg -> answer = "${updatedNumInput * 0.01}"
                unit_dg -> answer = "${updatedNumInput * 0.1}"
                unit_g -> answer = "${updatedNumInput * 1}"
                unit_dag -> answer = "${updatedNumInput * 10}"
                unit_kg -> answer = "${updatedNumInput * 1000}"
                unit_t -> answer = "${updatedNumInput * 1000000}"
                unit_oz -> answer = "${updatedNumInput * 0.03527396}"
                unit_lb -> answer = "${updatedNumInput * 0.00220462}"
            }
            unit_dag -> when(selectedItemTo) {
                unit_mg -> answer = "${updatedNumInput * 0.0001}"
                unit_cg -> answer = "${updatedNumInput * 0.001}"
                unit_dg -> answer = "${updatedNumInput * 0.01}"
                unit_g -> answer = "${updatedNumInput * 0.1}"
                unit_dag -> answer = "${updatedNumInput * 1}"
                unit_kg -> answer = "${updatedNumInput * 100}"
                unit_t -> answer = "${updatedNumInput * 100000}"
                unit_oz -> answer = "${updatedNumInput * 0.00003527396}"
                unit_lb -> answer = "${updatedNumInput * 0.00000220462}"
            }
            unit_kg -> when(selectedItemTo) {
                unit_mg -> answer = "${updatedNumInput * 0.000001}"
                unit_cg -> answer = "${updatedNumInput * 0.00001}"
                unit_dg -> answer = "${updatedNumInput * 0.0001}"
                unit_g -> answer = "${updatedNumInput * 0.001}"
                unit_dag -> answer = "${updatedNumInput * 0.01}"
                unit_kg -> answer = "${updatedNumInput * 1}"
                unit_t -> answer = "${updatedNumInput * 1000}"
                unit_oz -> answer = "${updatedNumInput * 35.27396}"
                unit_lb -> answer = "${updatedNumInput * 2.20462}"
            }
            unit_t -> when(selectedItemTo) {
                unit_mg -> answer = "${updatedNumInput * 0.000000001}"
                unit_cg -> answer = "${updatedNumInput * 0.00000001}"
                unit_dg -> answer = "${updatedNumInput * 0.0000001}"
                unit_g -> answer = "${updatedNumInput * 0.000001}"
                unit_dag -> answer = "${updatedNumInput * 0.00001}"
                unit_kg -> answer = "${updatedNumInput * 0.001}"
                unit_t -> answer = "${updatedNumInput * 1}"
                unit_oz -> answer = "${updatedNumInput * 0.00003527396}"
                unit_lb -> answer = "${updatedNumInput * 0.00000220462}"
            }
            unit_oz -> when(selectedItemTo) {
                unit_mg -> answer = "${updatedNumInput * 28349.5}"
                unit_cg -> answer = "${updatedNumInput * 2834.95}"
                unit_dg -> answer = "${updatedNumInput * 283.495}"
                unit_g -> answer = "${updatedNumInput * 28.3495}"
                unit_dag -> answer = "${updatedNumInput * 2.83495}"
                unit_kg -> answer = "${updatedNumInput * 0.0283495}"
                unit_t -> answer = "${updatedNumInput * 0.0000283495}"
                unit_oz -> answer = "${updatedNumInput * 1}"
                unit_lb -> answer = "${updatedNumInput * 0.0625}"
            }
            unit_lb -> when(selectedItemTo) {
                unit_mg -> answer = "${updatedNumInput * 453592.37}"
                unit_cg -> answer = "${updatedNumInput * 45359.237}"
                unit_dg -> answer = "${updatedNumInput * 4535.9237}"
                unit_g -> answer = "${updatedNumInput * 453.59237}"
                unit_dag -> answer = "${updatedNumInput * 45.359237}"
                unit_kg -> answer = "${updatedNumInput * 0.45359237}"
                unit_t -> answer = "${updatedNumInput * 0.00045359237}"
                unit_oz -> answer = "${updatedNumInput * 16}"
                unit_lb -> answer = "${updatedNumInput * 1}"
            }
        }

        return answer ?: "Invalid conversion"
    }

    // -----------------------------------Unit converter - TEMPERATURE------------------------------
    fun getTemperatureConversionAnswer(updatedNumInput: Double, selectedItemFrom: Any?, selectedItemTo: Any?): String {
        val unit_celsius = "(°C) Celsius"
        val unit_fahrenheit = "(°F) Fahrenheit"
        val unit_kelvin = "(K) Kelvin"

        var answer: String? = null

        when (selectedItemFrom.toString()) {
            unit_celsius -> when (selectedItemTo) {
                unit_celsius -> answer = "${updatedNumInput * 1}"
                unit_fahrenheit -> answer = "${(updatedNumInput * 9/5) + 32}"
                unit_kelvin -> answer = "${updatedNumInput + 273.15}"
            }
            unit_fahrenheit -> when (selectedItemTo) {
                unit_celsius -> answer = "${(updatedNumInput - 32) * 5/9}"
                unit_fahrenheit -> answer = "${updatedNumInput * 1}"
                unit_kelvin -> answer = "${(updatedNumInput + 459.67) * 5/9}"
            }
            unit_kelvin -> when (selectedItemTo) {
                unit_celsius -> answer = "${updatedNumInput - 273.15}"
                unit_fahrenheit -> answer = "${(updatedNumInput * 9/5) - 459.67}"
                unit_kelvin -> answer = "${updatedNumInput * 1}"
            }
        }

        return answer ?: "Invalid conversion"
    }
}