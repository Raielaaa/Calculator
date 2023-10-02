package com.example.calculatorresponsivetest4.ui.history

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.calculatorresponsivetest4.databinding.FragmentHistoryItemBinding
import com.example.calculatorresponsivetest4.db.Entity
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object HistoryAdapter : RecyclerView.Adapter<HistoryAdapter.HistoryAdapterViewHolder>() {
    class HistoryAdapterViewHolder(private val _binding: FragmentHistoryItemBinding): RecyclerView.ViewHolder(_binding.root) {
        fun bind(entity: Entity) {
            _binding.apply {
                tvCurrentDateTime.text = entity.currentDateTime
                tvHistorySolution.text = entity.solution
                tvHistoryAnswer.text = entity.answer
                tvMode.text = entity.mode
            }
        }
    }

    private val collections = ArrayList<Entity>()

    fun updateList(): List<Entity> = collections

    @SuppressLint("NotifyDataSetChanged")
    fun setList(list: List<Entity>) {
        collections.clear()
        collections.addAll(list)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryAdapterViewHolder {
        val binding = FragmentHistoryItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HistoryAdapterViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return collections.size
    }

    override fun onBindViewHolder(holder: HistoryAdapterViewHolder, position: Int) {
        holder.bind(collections[position])
    }

    fun getCurrentDateTime(): String = SimpleDateFormat("EEEE, MMMM dd, hh:mm a", Locale.getDefault()).format(Date())
}