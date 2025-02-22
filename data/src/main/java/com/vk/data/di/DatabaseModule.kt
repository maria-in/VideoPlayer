package com.vk.data.di

import android.content.Context
import com.vk.data.player.datasource.local.VideoPlayerDatabase
import com.vk.data.player.datasource.local.dao.VideoPlayerDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideVideoPlayerDatabase(
        @ApplicationContext context: Context
    ): VideoPlayerDatabase {
        return VideoPlayerDatabase(context)
    }

    @Provides
    fun provideVideoPlayerDao(database: VideoPlayerDatabase): VideoPlayerDao {
        return database.videoPlayerDao
    }
}