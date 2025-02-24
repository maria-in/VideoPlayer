package com.vk.domain.repository

import kotlinx.coroutines.flow.Flow

interface SessionCacheService {
    suspend fun saveVideoPath(path: String)
    fun getVideoPath(): Flow<String?>
}