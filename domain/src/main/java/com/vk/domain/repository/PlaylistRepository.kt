package com.vk.domain.repository

import com.vk.domain.model.Video

interface PlaylistRepository {
    suspend fun getPlaylist(): List<Video>
}