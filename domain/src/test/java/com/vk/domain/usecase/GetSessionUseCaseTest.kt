package com.vk.domain.usecase

import com.vk.domain.repository.SessionCacheService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.Mockito.`when`
import org.mockito.kotlin.mock

class GetSessionUseCaseTest {

    private val sessionCacheService = mock<SessionCacheService>()
    private val getSessionUseCase = GetSessionUseCase(sessionCacheService)

    @Test
    fun `should return video path from sessionCacheService`() = runTest {
        val expectedPath = "video/path.mp4"
        val flow: Flow<String?> = flowOf(expectedPath)
        `when`(sessionCacheService.getVideoPath()).thenReturn(flow)

        val result = getSessionUseCase.invoke(GetSessionUseCase.Params)

        result.collect { path ->
            assertEquals(expectedPath, path)
        }
    }
}