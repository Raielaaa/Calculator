package com.example.calculatorresponsivetest4.ui.history

import android.os.Bundle
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.calculatorresponsivetest4.R
import com.example.calculatorresponsivetest4.databinding.FragmentHomeHistoryListDialogItemBinding
import com.example.calculatorresponsivetest4.databinding.FragmentHomeHistoryListDialogBinding
import com.example.calculatorresponsivetest4.db.DBViewModel
import com.example.calculatorresponsivetest4.db.DBViewModelFactory
import com.example.calculatorresponsivetest4.db.Database
import com.example.calculatorresponsivetest4.db.Entity

// TODO: Customize parameter argument names
const val ARG_ITEM_COUNT = "item_count"

/**
 *
 * A fragment that shows a list of items as a modal bottom sheet.
 *
 * You can show this modal bottom sheet from your activity like this:
 * <pre>
 *    HomeHistory.newInstance(30).show(supportFragmentManager, "dialog")
 * </pre>
 */
class HomeHistory : BottomSheetDialogFragment() {

    private var _binding: FragmentHomeHistoryListDialogBinding? = null
    private lateinit var dbViewModel: DBViewModel
    private lateinit var homeHistoryAdapter: HomeHistoryAdapter

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentHomeHistoryListDialogBinding.inflate(inflater, container, false)
        homeHistoryAdapter = HomeHistoryAdapter {
                entity: Entity -> clickedItem(entity)
        }

        dbViewModel = ViewModelProvider(this, DBViewModelFactory(Database.getInstance(requireContext()).dao()))[DBViewModel::class.java]
        dbViewModel.getAllEntityFromVM.observe(viewLifecycleOwner, Observer {
            homeHistoryAdapter.setList(it)
            homeHistoryAdapter.notifyDataSetChanged()
        })

        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val recyclerView = view.findViewById<RecyclerView>(R.id.list)
//        recyclerView?.adapter = arguments?.getInt(ARG_ITEM_COUNT)?.let { ItemAdapter(it) }
        recyclerView.adapter = homeHistoryAdapter
    }

    private fun clickedItem(entity: Entity) {

    }

    private inner class ViewHolder internal constructor(binding: FragmentHomeHistoryListDialogItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        internal val text: TextView = binding.tvHistoryFromBottomSheetDialogFragment
    }

    private inner class ItemAdapter internal constructor(private val mItemCount: Int) :
        RecyclerView.Adapter<ViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

            return ViewHolder(
                FragmentHomeHistoryListDialogItemBinding.inflate(
                    LayoutInflater.from(
                        parent.context
                    ), parent, false
                )
            )

        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.text.text = position.toString()
        }

        override fun getItemCount(): Int {
            return mItemCount
        }
    }

    companion object {

        // TODO: Customize parameters
        fun newInstance(itemCount: Int): HomeHistory =
            HomeHistory().apply {
                arguments = Bundle().apply {
                    putInt(ARG_ITEM_COUNT, itemCount)
                }
            }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}