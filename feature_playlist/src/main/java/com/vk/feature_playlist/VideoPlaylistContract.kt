package com.vk.feature_playlist

import com.vk.common.presentation.UiEffect
import com.vk.common.presentation.UiEvent
import com.vk.common.presentation.UiState
import com.vk.domain.model.Video

class VideoPlaylistContract {
    sealed class Event : UiEvent {
        data class OnVideoClicked(val videoUrl: String) : Event()
    }

    data class State(
        val isLoading: Boolean = true,
        val videoList: List<Video> = emptyList()
    ) : UiState

    sealed class Effect : UiEffect
}