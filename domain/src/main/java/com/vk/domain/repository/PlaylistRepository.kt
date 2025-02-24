package com.vk.domain.repository

import com.vk.domain.model.Video
import com.vk.domain.utils.CustomResult
import kotlinx.coroutines.flow.Flow

interface PlaylistRepository {
    suspend fun getPlaylist(withSync: Boolean): Flow<CustomResult<List<Video>>>
}