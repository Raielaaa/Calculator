package com.example.calculatorresponsivetest4.db

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
}