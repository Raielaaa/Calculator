package com.example.calculatorresponsivetest4.ui.history

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.calculatorresponsivetest4.databinding.FragmentHomeHistoryListDialogItemBinding
import com.example.calculatorresponsivetest4.db.Entity

class HomeHistoryAdapter(
    private val clickListener: (Entity) -> Unit
): RecyclerView.Adapter<HomeHistoryAdapter.HomeHistoryViewHolder>() {
    inner class HomeHistoryViewHolder(private val binding: FragmentHomeHistoryListDialogItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(entity: Entity, clickListener: (Entity) -> Unit) {
            binding.apply {
                tvHistoryFromBottomSheetDialogFragment.text = entity.history
            }
            binding.root.setOnClickListener {
                clickListener(entity)
            }
        }
    }

    private val collections: ArrayList<Entity> = ArrayList()

    fun setList(entity: List<Entity>) {
        collections.clear()
        collections.addAll(entity)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeHistoryViewHolder {
        val binding: FragmentHomeHistoryListDialogItemBinding = FragmentHomeHistoryListDialogItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false)
        return HomeHistoryViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return collections.size
    }

    override fun onBindViewHolder(holder: HomeHistoryViewHolder, position: Int) {
        holder.bind(collections[position], clickListener)
    }

}