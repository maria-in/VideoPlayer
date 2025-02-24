package com.vk.data.player.datasource.remote.models

import kotlinx.serialization.Serializable

@Serializable
data class VideoDto(
    val id: Int,
    val title: String,
    val thumbnailUrl: String,
    val duration: String,
    val uploadTime: String,
    val views: String,
    val author: String,
    val videoUrl: String,
    val description: String,
    val subscriber: String,
    val isLive: Boolean
)
