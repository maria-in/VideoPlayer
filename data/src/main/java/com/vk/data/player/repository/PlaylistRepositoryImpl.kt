package com.vk.data.player.repository

import com.vk.data.player.datasource.local.dao.VideoPlayerDao
import com.vk.data.player.datasource.local.model.VideoItemEntity
import com.vk.data.player.datasource.remote.api.VideoApi
import com.vk.data.player.mappers.toVideo
import com.vk.data.player.mappers.toVideoEntity
import com.vk.domain.model.Video
import com.vk.domain.repository.PlaylistRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class PlaylistRepositoryImpl @Inject constructor(
    private val videoApi: VideoApi,
    private val localSource: VideoPlayerDao
) : PlaylistRepository {

    override suspend fun getPlaylist(withSync: Boolean): Flow<List<Video>> =
        withContext(Dispatchers.IO) {
            if (withSync) {
                videoApi.getVideos().onSuccess {
                    val list = it.map { dto -> dto.toVideoEntity() }
                    localSource.clearCache()
                    localSource.saveVideoPlaylist(list)
                }.onFailure {
                    listOf<VideoItemEntity>()
                }
            }
            return@withContext localSource.getVideoPlaylist()
                .map { videoList -> videoList.map { it.toVideo() } }
        }
}