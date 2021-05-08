package com.mt.budgie20.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

private const val USER_PREF = "settings"
private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = USER_PREF)

data class UserPreferences(
    val showCompleted: Boolean,
)

class DataStoreRepository(
    val context: Context
) {

    private object PreferencesKeys {
        val SHOW_COMPLETED = booleanPreferencesKey("show_completed")
    }

    val onBoardStatus = context.dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }.map { pref ->
            val showCompleted = pref[PreferencesKeys.SHOW_COMPLETED] ?: false
            UserPreferences(showCompleted)
        }

    suspend fun updateShowCompleted(showCompleted: Boolean) {
        context.dataStore.edit { pref ->
            pref[PreferencesKeys.SHOW_COMPLETED] = showCompleted
        }
    }


}


