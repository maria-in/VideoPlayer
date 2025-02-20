package com.vk.data.player.repository

import com.vk.data.player.datasource.remote.api.VideoApi
import com.vk.data.player.mappers.toVideo
import com.vk.domain.model.Video
import com.vk.domain.repository.PlaylistRepository
import retrofit2.awaitResponse
import javax.inject.Inject

class PlaylistRepositoryImpl @Inject constructor(
    private val videoApi: VideoApi
): PlaylistRepository {

    override suspend fun getPlaylist(): List<Video> {
        val response = videoApi.getVideos()
        return response.awaitResponse().body()?.map { it.toVideo() } ?: emptyList()
    }
}