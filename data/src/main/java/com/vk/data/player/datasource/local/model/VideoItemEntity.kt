package com.vk.data.player.datasource.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "video_item_table")
data class VideoItemEntity(
    @PrimaryKey val id: Int,
    val title: String,
    val duration: String,
    val thumbnailUrl: String,
    val videoUrl: String
)
