package com.example.calculatorresponsivetest4.ui.history

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.calculatorresponsivetest4.MainActivity
import com.example.calculatorresponsivetest4.databinding.FragmentHistoryBinding
import com.example.calculatorresponsivetest4.db.DBViewModel
import com.example.calculatorresponsivetest4.db.DBViewModelFactory
import com.example.calculatorresponsivetest4.db.Database

class HistoryFragment : Fragment() {
    private lateinit var binding: FragmentHistoryBinding
    private lateinit var dbViewModel: DBViewModel
    private val TAG: String = "MyTag"

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (requireActivity() as MainActivity).isInHistoryFragment = true
        requireActivity().invalidateMenu()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        (requireActivity() as MainActivity).isInHistoryFragment = false
        requireActivity().invalidateMenu()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentHistoryBinding.inflate(inflater, container, false)
        dbViewModel = ViewModelProvider(this, DBViewModelFactory(Database.getInstance(requireContext()).dao()))[DBViewModel::class.java]

        initRecyclerView()
        initViews()

        return binding.root
    }

    private fun initViews() {
        binding.apply {
            if (HistoryAdapter.updateList().isEmpty()) tvStatus.visibility = View.VISIBLE
            else tvStatus.visibility = View.INVISIBLE
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun initRecyclerView() {
        binding.rvMain.adapter = HistoryAdapter
        HistoryAdapter.updateList()
        HistoryAdapter.notifyDataSetChanged()
    }
}