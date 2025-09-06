package com.example.dayup.data

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore by preferencesDataStore("settings")

class AppPreferences(private val context: Context) {
    companion object {
        private val THEME_KEY = booleanPreferencesKey("dar_theme")
        private val COUNTER_KEY = intPreferencesKey("counter")
    }

    suspend fun saveTheme(isDarkMode: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[THEME_KEY] = isDarkMode
        }
    }

    fun getTheme(): Flow<Boolean> {
        return context.dataStore.data.map { preferences ->
            preferences[THEME_KEY] ?: false
        }
    }

    suspend fun saveCounter(value: Int) {
        context.dataStore.edit { preferences ->
            preferences[COUNTER_KEY] = value
        }
    }

    fun getCounter(): Flow<Int> {
        return context.dataStore.data.map { preferences ->
            preferences[COUNTER_KEY] ?: 0
        }
    }
}