package com.vk.feature_videoplayer.presentaion

import com.vk.common.presentation.UiEffect
import com.vk.common.presentation.UiEvent
import com.vk.common.presentation.UiState

class PlayerScreenContract {
    sealed class Event : UiEvent {
        data class OnVideoClicked(val videoId: Int) : Event()
    }

    data class State(
        val isLoading: Boolean = true,
    ) : UiState

    sealed class Effect : UiEffect
}