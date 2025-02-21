package com.vk.feature_videoplayer.service

import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import javax.inject.Inject

class VideoServiceHandler @Inject constructor(
    private val exoPlayer: ExoPlayer
) : Player.Listener {

    init {
        exoPlayer.addListener(this)
    }

    fun getPlayer(): ExoPlayer {
        return exoPlayer
    }

    //TODO: add loading
    var isLoading: Boolean = true
    override fun onPlaybackStateChanged(playbackState: Int) {
        isLoading = playbackState == Player.STATE_BUFFERING
    }

    fun setMediaItem(mediaItem: MediaItem) {
        exoPlayer.setMediaItem(mediaItem)
        exoPlayer.prepare()
    }

    fun releasePlayer() {
        exoPlayer.release()
    }
}