package com.vk.domain.usecase

import com.vk.common.domain.BaseUseCase
import com.vk.domain.repository.SessionCacheService
import javax.inject.Inject

class SaveSessionUseCase @Inject constructor(
    private val sessionCacheService: SessionCacheService
) : BaseUseCase<Unit, SaveSessionUseCase.Params>() {

    override suspend fun execute(params: Params) {
        sessionCacheService.saveVideoPath(params.path)
    }
    data class Params(val path: String)
}