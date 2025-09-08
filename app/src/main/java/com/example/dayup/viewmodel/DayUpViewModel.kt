package com.example.dayup.viewmodel

import android.content.Context
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dayup.data.AppPreferences
import com.example.dayup.data.GitHubRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.threeten.bp.LocalDate

class DayUpViewModel(context: Context) : ViewModel() {
    private val appPreferences = AppPreferences(context)
    private val gitHubRepo = GitHubRepository()

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

    var hasCommitToday = mutableStateOf(false)
        private set

    var taskTitle = mutableStateOf("Estudar programação")
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

    suspend fun checkTodayCommit(username: String): Boolean {
        return try {
            val repos = gitHubRepo.getUserRepos(username)
            val today = LocalDate.now()
            repos.any { it.pushedAt.toLocalDate() == today }
        } catch (e: Exception) {
            false
        }
    }
}