package com.vk.ui_kit

data class VideoItem(
    val id: Long,
    val title: String,
    val duration: String,
    val previewUrl: String? = null
)
