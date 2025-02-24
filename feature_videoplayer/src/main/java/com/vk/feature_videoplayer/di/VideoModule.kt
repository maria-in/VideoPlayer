package com.vk.feature_videoplayer.di

import android.content.Context
import androidx.media3.exoplayer.ExoPlayer
import com.vk.feature_videoplayer.service.VideoServiceHandler
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object VideoModule {

    @Provides
    @Singleton
    fun provideExoPlayer(
        @ApplicationContext context: Context,
    ): ExoPlayer = ExoPlayer.Builder(context)
        .build().apply {
            repeatMode = ExoPlayer.REPEAT_MODE_OFF
            playWhenReady = true
        }

    @Provides
    @Singleton
    fun provideAudioServiceHandler(exoPlayer: ExoPlayer): VideoServiceHandler =
        VideoServiceHandler(exoPlayer)
}