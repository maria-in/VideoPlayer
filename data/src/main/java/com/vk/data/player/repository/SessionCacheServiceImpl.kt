package com.vk.data.player.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.vk.domain.repository.SessionCacheService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SessionCacheServiceImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>
): SessionCacheService {

    private val VIDEO_PATH_KEY = stringPreferencesKey("video_path")

    override suspend fun saveVideoPath(path: String) {
        dataStore.edit { preferences ->
            preferences[VIDEO_PATH_KEY] = path
        }
    }

    override fun getVideoPath(): Flow<String?> {
        return dataStore.data.map { preferences ->
            preferences[VIDEO_PATH_KEY]
        }
    }
}