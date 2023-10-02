package com.example.calculatorresponsivetest4.db

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.calculatorresponsivetest4.ui.history.HistoryAdapter
import kotlinx.coroutines.launch

class DBViewModel(private val dao: Dao): ViewModel() {
    fun insertEntityFromVM(entity: Entity) = viewModelScope.launch {
        dao.insertEntity(entity)
    }
    fun deleteEntityFromVM(entity: Entity) = viewModelScope.launch {
        dao.deleteEntity(entity)
    }
    fun deleteAllEntityFromVM() = viewModelScope.launch {
        dao.deleteAllEntity()
    }
    val getAllEntityFromVM: LiveData<List<Entity>> = dao.getAllEntity()

    init {
        getAllEntityFromVM.observeForever {
            HistoryAdapter.setList(it)
        }
    }
}