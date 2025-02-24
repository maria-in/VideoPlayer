package com.vk.domain.usecase

import com.vk.common.domain.BaseUseCase
import com.vk.domain.repository.SessionCacheService
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetSessionUseCase @Inject constructor(
    private val sessionCacheService: SessionCacheService
) : BaseUseCase<Flow<String?>, GetSessionUseCase.Params>() {

    override suspend fun execute(params: Params): Flow<String?> {
        return sessionCacheService.getVideoPath()
    }

    data object Params
}