package com.vk.data.player.datasource.remote.api

import com.vk.data.player.datasource.remote.models.VideoDto
import retrofit2.Call
import retrofit2.http.GET

//1046540-videopla-9B21AC4A
interface VideoApi {
    @GET("videos")
    fun getVideos(): Call<List<VideoDto>>
}