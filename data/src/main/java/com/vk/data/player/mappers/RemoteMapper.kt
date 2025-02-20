package com.vk.data.player.mappers

import com.vk.data.player.datasource.remote.models.VideoDto
import com.vk.domain.model.Video

fun VideoDto.toVideo() = Video(
    id = this.id,
    title = this.title,
    duration = this.duration
)