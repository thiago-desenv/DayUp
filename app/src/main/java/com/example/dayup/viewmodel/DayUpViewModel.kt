package com.example.dayup.viewmodel

import android.content.Context
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dayup.data.AppPreferences
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class DayUpViewModel(context: Context) : ViewModel() {
    private val AppPreferences = AppPreferences(context)

    var darkThemeEnabled: StateFlow<Boolean> = AppPreferences.getTheme()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), false)

    var count: StateFlow<Int> = AppPreferences.getCounter()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0)

    var taskTitle = mutableStateOf("Estudar")
        private set

    fun toggleTheme() {
        viewModelScope.launch {
            AppPreferences.saveTheme(!darkThemeEnabled.value)
        }
    }

    fun incrementCounter() {
        viewModelScope.launch {
            val current = count.value
            AppPreferences.saveCounter(current + 1)
        }
    }

    fun setTaskTitle(title: String) {
        taskTitle.value = title
    }
}