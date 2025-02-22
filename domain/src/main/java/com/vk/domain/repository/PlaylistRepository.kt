package com.vk.domain.repository

import com.vk.domain.model.Video
import kotlinx.coroutines.flow.Flow

interface PlaylistRepository {
    suspend fun getPlaylist(withSync: Boolean): Flow<List<Video>>
}