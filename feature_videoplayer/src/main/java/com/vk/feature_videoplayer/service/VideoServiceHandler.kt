package com.vk.feature_videoplayer.service

import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

class VideoServiceHandler @Inject constructor(
    private val exoPlayer: ExoPlayer
) : Player.Listener {

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    init {
        exoPlayer.addListener(this)
    }

    fun getPlayer(): ExoPlayer {
        return exoPlayer
    }

    override fun onPlaybackStateChanged(playbackState: Int) {
        _isLoading.value = playbackState == Player.STATE_BUFFERING
    }

    fun setMediaItem(mediaItem: MediaItem) {
        exoPlayer.setMediaItem(mediaItem)
        exoPlayer.prepare()
    }

    fun releasePlayer() {
        exoPlayer.release()
    }
}