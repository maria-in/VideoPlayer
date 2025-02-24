package com.vk.ui_kit

data class VideoItem(
    val id: Int,
    val title: String,
    val duration: String,
    val previewUrl: String? = null,
    val videoUrl: String
)
