package com.vk.feature_playlist

import android.content.Context
import com.vk.domain.usecase.GetPlaylistUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import androidx.lifecycle.viewModelScope
import com.vk.common.presentation.BaseViewModel
import com.vk.common.utils.isInternetAvailable
import com.vk.domain.usecase.SaveSessionUseCase
import com.vk.domain.utils.CustomResult
import com.vk.feature_playlist.utils.toStringRes
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch

@HiltViewModel
class VideoPlaylistViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val getPlaylistUseCase: GetPlaylistUseCase,
    private val saveSessionUseCase: SaveSessionUseCase,
) : BaseViewModel<VideoPlaylistContract.Event, VideoPlaylistContract.State, VideoPlaylistContract.Effect>() {

    init {
        loadVideo()
    }

    private fun loadVideo() = viewModelScope.launch {
        setState { copy(isLoading = true) }
        getPlaylistUseCase.invoke(GetPlaylistUseCase.Params(isInternetAvailable(context)))
            .collect { result ->
                when(result) {
                    is CustomResult.Success -> {
                        setState { copy(isLoading = false, videoList = result.data ?: listOf()) }
                    }
                    is CustomResult.Error -> {
                        setState { copy(isLoading = false, errorStringRes = result.issueType?.toStringRes()) }
                    }
                }
            }
    }

    override fun createInitialState() = VideoPlaylistContract.State()

    override fun handleEvent(event: VideoPlaylistContract.Event) {
        when (event) {
            is VideoPlaylistContract.Event.OnVideoClicked -> {
                saveVideoPath(event.videoUrl)
            }

            is VideoPlaylistContract.Event.OnReload -> {
                loadVideo()
            }
        }
    }

    private fun saveVideoPath(videoUrl: String) = viewModelScope.launch {
        saveSessionUseCase.invoke(SaveSessionUseCase.Params(videoUrl))
        setEffect { VideoPlaylistContract.Effect.NavigateToVideoScreen }
    }
}