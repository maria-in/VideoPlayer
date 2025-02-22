package com.vk.data.player.datasource.remote.api

import com.vk.data.player.datasource.remote.models.VideoDto
import retrofit2.http.GET

interface VideoApi {
    @GET("videos")
    suspend fun getVideos(): Result<List<VideoDto>>
}