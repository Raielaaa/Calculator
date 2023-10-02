package com.example.calculatorresponsivetest4.ui.history

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.example.calculatorresponsivetest4.R
import com.example.calculatorresponsivetest4.db.DBViewModel
import com.example.calculatorresponsivetest4.db.DBViewModelFactory
import com.example.calculatorresponsivetest4.db.Database
import com.example.calculatorresponsivetest4.utils.CustomTypefaceSpan

class DeleteHistoryDialogFragment : DialogFragment() {
    private lateinit var dbViewModel: DBViewModel
    @SuppressLint("NotifyDataSetChanged")
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        dbViewModel = ViewModelProvider(this, DBViewModelFactory(Database.getInstance(requireContext()).dao()))[DBViewModel::class.java]

        return activity?.let {
            // Use the Builder class for convenient dialog construction.
            val builder = AlertDialog.Builder(it)
            builder.setTitle("Confirm action")
            builder.setMessage("Delete all history? This action cannot be undone.")
                .setPositiveButton(textStyle("Delete", applyColor = true)) { dialog, id ->
                    dbViewModel.deleteAllEntityFromVM()
                    HistoryAdapter.notifyDataSetChanged()
                    HistoryAdapter.updateList()

                    Toast.makeText(requireContext(), "Delete successful", Toast.LENGTH_LONG).show()
                }
                .setNegativeButton(textStyle("Cancel", applyColor = true)) { dialog, id ->
                    // User cancelled the dialog.
                }
            // Create the AlertDialog object and return it.
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }

    fun textStyle(message: String, applyColor: Boolean) : SpannableString {
        val customTypeface = ResourcesCompat.getFont(requireContext(), R.font.acme)!!
        val spannableString = SpannableString(message)
        spannableString.setSpan(
            CustomTypefaceSpan(customTypeface),
            0,
            spannableString.length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        // Apply ForegroundColorSpan to change text color
        if (applyColor) {
            spannableString.setSpan(
                ForegroundColorSpan(requireContext().getColor(R.color.operator)),
                0,
                spannableString.length,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
        }

        return spannableString
    }
}