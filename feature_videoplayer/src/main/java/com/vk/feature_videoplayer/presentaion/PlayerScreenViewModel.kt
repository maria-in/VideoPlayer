package com.vk.feature_videoplayer.presentaion

import android.net.Uri
import android.util.Log
import androidx.core.net.toUri
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.media3.common.MediaItem
import com.vk.common.presentation.BaseViewModel
import com.vk.domain.usecase.GetSessionUseCase
import com.vk.feature_videoplayer.service.VideoServiceHandler
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlayerScreenViewModel @Inject constructor(
    private val getSessionUseCase: GetSessionUseCase,
    private val savedStateHandle: SavedStateHandle,
    private val audioServiceHandler: VideoServiceHandler,
): BaseViewModel<PlayerScreenContract.Event, PlayerScreenContract.State, PlayerScreenContract.Effect>() {

    override fun createInitialState() = PlayerScreenContract.State(exoPlayer = audioServiceHandler.getPlayer())

    init {
        loadVideo()
    }

    private fun loadVideo() = viewModelScope.launch {
        getSessionUseCase.invoke(GetSessionUseCase.Params).collect {
            Log.println(Log.DEBUG, "IIII", it.toString())
            it?.toUri()?.let { videoUri -> playVideo(videoUri) }
        }
    }

    fun addVideoUri(uri: Uri) {
        savedStateHandle["videoUri"] = uri
        audioServiceHandler.setMediaItem(MediaItem.fromUri(uri))
//        player.addMediaItem(MediaItem.fromUri(uri))
    }

    private fun playVideo(uri: Uri) {
        audioServiceHandler.setMediaItem(MediaItem.fromUri(uri))

//        player.setMediaItem(MediaItem.fromUri(uri))
//        player.setMediaItem(uri
//            videoItems.value.find { it.contentUri == uri }?.mediaItem ?: return
//        )
    }

    override fun onCleared() {
        super.onCleared()
        //TODO: handle release
//        audioServiceHandler.releasePlayer()
    }

    override fun handleEvent(event: PlayerScreenContract.Event) {
        TODO("Not yet implemented")
    }
}