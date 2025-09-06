package com.example.dayup.viewmodel

import android.content.Context
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dayup.data.AppPreferences
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class DayUpViewModel(context: Context) : ViewModel() {
    private val appPreferences = AppPreferences(context)

    var darkThemeEnabled: StateFlow<Boolean> = appPreferences.getTheme()
        .stateIn(viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            runBlocking { appPreferences.getTheme().first() }
        )

    var count: StateFlow<Int> = appPreferences.getCounter()
        .stateIn(viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            runBlocking { appPreferences.getCounter().first() }
        )

    var taskTitle = mutableStateOf("Estudar")
        private set

    fun toggleTheme() {
        viewModelScope.launch {
            appPreferences.saveTheme(!darkThemeEnabled.value)
        }
    }

    fun incrementCounter() {
        viewModelScope.launch {
            val current = count.value
            appPreferences.saveCounter(current + 1)
        }
    }

    fun setTaskTitle(title: String) {
        taskTitle.value = title
    }
}