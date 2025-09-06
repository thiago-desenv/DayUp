package com.example.dayup.viewmodel

import android.content.Context
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dayup.data.ThemePreference
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class DayUpViewModel(context: Context) : ViewModel() {
    private val themePref = ThemePreference(context)

    var darkThemeEnabled: StateFlow<Boolean> = themePref.getTheme()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), false)

    var count = mutableIntStateOf(0)
        private set

    var taskTitle = mutableStateOf("Estudar")
        private set

    fun toggleTheme() {
        viewModelScope.launch {
            themePref.saveTheme(!darkThemeEnabled.value)
        }
    }

    fun incrementCounter() {
        count.intValue++
    }

    fun setTaskTitle(title: String) {
        taskTitle.value = title
    }
}