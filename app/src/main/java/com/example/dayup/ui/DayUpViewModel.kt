package com.example.dayup.ui

import androidx.lifecycle.ViewModel

class DayUpViewModel : ViewModel() {
    var darkThemeEnabled = androidx.compose.runtime.mutableStateOf(false)
        private set

    var count = androidx.compose.runtime.mutableIntStateOf(0)
        private set

    var taskTitle = androidx.compose.runtime.mutableStateOf("Estudar")
        private set

    fun toggleTheme() {
        darkThemeEnabled.value = !darkThemeEnabled.value
    }

    fun incrementCounter() {
        count.intValue++
    }

    fun setTaskTitle(title: String) {
        taskTitle.value = title
    }
}