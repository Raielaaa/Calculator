package com.example.calculatorresponsivetest4.utils

import android.graphics.Typeface
import android.text.TextPaint
import android.text.style.TypefaceSpan

class CustomTypefaceSpan(private val typeface: Typeface) : TypefaceSpan("") {
    override fun updateDrawState(ds: TextPaint) {
        applyCustomTypeface(ds)
    }

    override fun updateMeasureState(paint: TextPaint) {
        applyCustomTypeface(paint)
    }

    private fun applyCustomTypeface(paint: TextPaint) {
        val oldTypeface = paint.typeface
        val newTypeface = Typeface.create(typeface, oldTypeface?.style ?: Typeface.NORMAL)
        paint.typeface = newTypeface
    }
}