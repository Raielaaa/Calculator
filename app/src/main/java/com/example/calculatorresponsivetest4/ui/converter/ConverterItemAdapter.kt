package com.example.calculatorresponsivetest4.ui.converter

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.calculatorresponsivetest4.R
import com.example.calculatorresponsivetest4.databinding.ConverterModeListBinding

class ConverterItemAdapter (
    private val collections: List<ConverterItemModel>,
    private val rvTopSelection: RecyclerView,
    private val context: Context,
    private val clickListener: () -> Unit
): RecyclerView.Adapter<ConverterItemAdapter.ConverterItemViewHolder>() {
    inner class ConverterItemViewHolder(private val _binding: ConverterModeListBinding): RecyclerView.ViewHolder(_binding.root) {
        fun bind(currencyItemModel: ConverterItemModel, context: Context, clickListener: () -> Unit) {
            _binding.apply {
                btnItem.text = currencyItemModel.listItemName
                ivIcon.setImageURI(currencyItemModel.listItemIcon)
                root.setOnClickListener {
                    clickListener()
                    updateItemsToDefaultColor()
                    updateSelectedColor(cvMain, btnItem)
                }
            }
        }
    }

    private fun updateItemsToDefaultColor() {
        for (position in collections.indices) {
            val itemView = rvTopSelection.findViewHolderForAdapterPosition(position)?.itemView
            val cvMain = itemView?.findViewById<CardView>(R.id.cvMain)
            val btnItem = itemView?.findViewById<Button>(R.id.btnItem)
            if (cvMain != null && btnItem != null) {
                val updatedBGColor = ContextCompat.getColor(context, R.color.enabledColorUnitConverter)
                cvMain.setCardBackgroundColor(updatedBGColor)
                btnItem.setBackgroundColor(updatedBGColor)
                btnItem.setTextColor(ContextCompat.getColor(context, R.color.enabledColor))
            }
        }
    }

    private fun updateSelectedColor(cvMain: CardView, btnItem: Button) {
        val updatedColor: Int = ContextCompat.getColor(context, R.color.unitConverterClickedItemBGColor)
        cvMain.setCardBackgroundColor(updatedColor)
        btnItem.apply {
            setBackgroundColor(updatedColor)
            setTextColor(Color.WHITE)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ConverterItemViewHolder {
        val binding = ConverterModeListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ConverterItemViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return collections.size
    }

    override fun onBindViewHolder(holder: ConverterItemViewHolder, position: Int) {
        holder.bind(collections[position], context, clickListener)
    }
}