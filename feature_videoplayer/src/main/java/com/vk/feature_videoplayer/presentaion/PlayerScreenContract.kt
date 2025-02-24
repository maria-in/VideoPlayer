package com.vk.feature_videoplayer.presentaion

import androidx.media3.exoplayer.ExoPlayer
import com.vk.common.presentation.UiEffect
import com.vk.common.presentation.UiEvent
import com.vk.common.presentation.UiState

class PlayerScreenContract {
    sealed class Event : UiEvent {
        data class OnVideoClicked(val videoId: Int) : Event()
    }

    data class State(
        val isLoading: Boolean = true,
        val exoPlayer: ExoPlayer? = null
    ) : UiState

    sealed class Effect : UiEffect
}