package com.vk.data.player.mappers

import com.vk.data.player.datasource.local.model.VideoItemEntity
import com.vk.data.player.datasource.remote.models.VideoDto
import com.vk.domain.model.Video

fun VideoDto.toVideoEntity() = VideoItemEntity(
    id = this.id,
    title = this.title,
    duration = this.duration,
    thumbnailUrl = this.thumbnailUrl,
    videoUrl = this.videoUrl
)

fun VideoItemEntity.toVideo() = Video(
    id = this.id,
    title = this.title,
    duration = this.duration,
    previewUrl = this.thumbnailUrl,
    videoUrl = this.videoUrl
)