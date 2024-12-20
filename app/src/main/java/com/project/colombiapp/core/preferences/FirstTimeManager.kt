package com.project.colombiapp.core.preferences

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

val Context.firstTimeManager: DataStore<Preferences> by preferencesDataStore("firstTimeManager")

class FirstTimeManager @Inject constructor(@ApplicationContext val context: Context) {

    companion object {
        val FIRST_TIME_KEY = booleanPreferencesKey("firstTimeKey")
    }

    suspend fun getFirstTime(): Flow<Boolean> {
        return context.firstTimeManager.data.map {
            it[FIRST_TIME_KEY] ?: true
        }
    }

    suspend fun saveFirstTime(value: Boolean) {
        context.firstTimeManager.edit {
            it[FIRST_TIME_KEY] = value
        }
    }

}