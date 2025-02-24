package com.vk.data.di

import com.vk.data.player.repository.SessionCacheServiceImpl
import com.vk.domain.repository.SessionCacheService
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class SessionModule {

    @Binds
    @Singleton
    abstract fun bindSessionService(sessionCacheServiceImpl: SessionCacheServiceImpl): SessionCacheService
}