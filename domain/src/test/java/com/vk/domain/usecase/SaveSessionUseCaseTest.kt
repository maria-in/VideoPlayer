package com.vk.domain.usecase

import com.vk.domain.repository.SessionCacheService
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.mockito.Mockito.verify
import org.mockito.kotlin.mock

class SaveSessionUseCaseTest {

    private var sessionCacheService = mock<SessionCacheService>()
    private val saveSessionUseCase = SaveSessionUseCase(sessionCacheService)

    @Test
    fun `should call saveVideoPath with correct path`() = runTest {
        val expectedPath = "video/path.mp4"
        val params = SaveSessionUseCase.Params(expectedPath)

        saveSessionUseCase.invoke(params)

        verify(sessionCacheService).saveVideoPath(expectedPath)
    }
}