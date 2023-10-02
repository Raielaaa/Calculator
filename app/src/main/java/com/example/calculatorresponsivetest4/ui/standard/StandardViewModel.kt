package com.example.calculatorresponsivetest4.ui.standard

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class StandardViewModel : ViewModel() {
    private val TAG: String = "MyTag"

    val initialEquation: MutableLiveData<String> by lazy {
        MutableLiveData<String>().apply {
            value = ""
        }
    }

    val isPercentUsed: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>().apply {
            value = false
        }
    }

    val isEqualsSelected: MutableLiveData<Boolean> = MutableLiveData(false)

    val answerFromVM: MutableLiveData<String> by lazy {
        MutableLiveData<String>().apply {
            value = ""
        }
    }

    val recentHistory: MutableLiveData<String> by lazy {
        MutableLiveData<String>().apply {
            value = ""
        }
    }

    val isMemoryButtonSelected: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>()
    }

    val negateCount: MutableLiveData<Int> by lazy {
        MutableLiveData<Int>().apply {
            value = 0
        }
    }

    val historyForBottomSheetDialog: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    override fun onCleared() {
        super.onCleared()
        initialEquation.value = ""
    }
}