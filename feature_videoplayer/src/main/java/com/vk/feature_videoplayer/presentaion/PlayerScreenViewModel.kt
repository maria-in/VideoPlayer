package com.vk.feature_videoplayer.presentaion

import android.net.Uri
import androidx.core.net.toUri
import androidx.lifecycle.viewModelScope
import androidx.media3.common.MediaItem
import com.vk.common.presentation.BaseViewModel
import com.vk.domain.usecase.GetSessionUseCase
import com.vk.feature_videoplayer.service.VideoServiceHandler
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlayerScreenViewModel @Inject constructor(
    private val getSessionUseCase: GetSessionUseCase,
    private val audioServiceHandler: VideoServiceHandler,
): BaseViewModel<PlayerScreenContract.Event, PlayerScreenContract.State, PlayerScreenContract.Effect>() {

    override fun createInitialState() = PlayerScreenContract.State(exoPlayer = audioServiceHandler.getPlayer())

    init {
        audioServiceHandler.isLoading.onEach {
            setState { copy(isLoading = it) }
        }.launchIn(viewModelScope)

        loadVideo()
    }

    private fun loadVideo() = viewModelScope.launch {
        getSessionUseCase.invoke(GetSessionUseCase.Params).collect {
            it?.toUri()?.let { videoUri -> playVideo(videoUri) }
        }
    }

    private fun playVideo(uri: Uri) {
        audioServiceHandler.setMediaItem(MediaItem.fromUri(uri))
    }

    override fun handleEvent(event: PlayerScreenContract.Event) {}
}