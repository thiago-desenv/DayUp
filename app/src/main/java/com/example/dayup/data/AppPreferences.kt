package com.example.dayup.data

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.threeten.bp.LocalDate

val Context.dataStore by preferencesDataStore("settings")

class AppPreferences(private val context: Context) {
    companion object {
        private val THEME_KEY = booleanPreferencesKey("dar_theme")
        private val COUNTER_KEY = intPreferencesKey("counter")
        private val LAST_COMMIT_DATE_KEY = stringPreferencesKey("last_commit_date")
        private val USERNAME_KEY = stringPreferencesKey("username")
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

    suspend fun saveLastCommitDate(date: LocalDate) {
        context.dataStore.edit { preferences ->
            preferences[LAST_COMMIT_DATE_KEY] = date.toString()
        }
    }

    fun getLastCommitDate(): Flow<LocalDate?> {
        return context.dataStore.data.map { preferences ->
            preferences[LAST_COMMIT_DATE_KEY]?.let { LocalDate.parse(it) }
        }
    }

    suspend fun saveUsername(username: String) {
        context.dataStore.edit { preferences ->
            preferences[USERNAME_KEY] = username
        }
    }

    fun getUsername(): Flow<String> {
        return context.dataStore.data.map { preferences ->
            preferences[USERNAME_KEY] ?: ""
        }
    }
}