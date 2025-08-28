package com.example.dayup.viewmodel

import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class DayUpViewModel : ViewModel() {
    var darkThemeEnabled = mutableStateOf(false)
        private set

    var count = mutableIntStateOf(0)
        private set

    var taskTitle = mutableStateOf("Estudar")
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