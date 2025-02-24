package com.vk.domain.usecase

import com.vk.domain.model.Video
import com.vk.domain.repository.PlaylistRepository
import com.vk.domain.utils.CustomResult
import com.vk.domain.utils.NetworkIssues
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import org.junit.Assert.fail
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.mock

class GetPlaylistUseCaseTest {

    private var playlistRepository = mock<PlaylistRepository>()
    private lateinit var getPlaylistUseCase: GetPlaylistUseCase

    private val videoList = listOf(
        Video(
            id = 1,
            title = "Kotlin Basics",
            duration = "10:15",
            previewUrl = "https://example.com/previews/kotlin_basics.jpg",
            videoUrl = "https://example.com/videos/kotlin_basics.mp4"
        ),
        Video(
            id = 2,
            title = "Advanced Kotlin",
            duration = "20:30",
            previewUrl = "https://example.com/previews/advanced_kotlin.jpg",
            videoUrl = "https://example.com/videos/advanced_kotlin.mp4"
        ),
        Video(
            id = 3,
            title = "Coroutines in Kotlin",
            duration = "15:45",
            previewUrl = "https://example.com/previews/coroutines_kotlin.jpg",
            videoUrl = "https://example.com/videos/coroutines_kotlin.mp4"
        ),
        Video(
            id = 4,
            title = "Kotlin for Android Development",
            duration = "25:10",
            previewUrl = "https://example.com/previews/kotlin_android.jpg",
            videoUrl = "https://example.com/videos/kotlin_android.mp4"
        ),
        Video(
            id = 5,
            title = "Unit Testing in Kotlin",
            duration = "12:50",
            previewUrl = "https://example.com/previews/unit_testing_kotlin.jpg",
            videoUrl = "https://example.com/videos/unit_testing_kotlin.mp4"
        )
    )

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        getPlaylistUseCase = GetPlaylistUseCase(playlistRepository)
    }

    @Test
    fun `should return video list from repository with sync`() = runTest {
        val mockVideos = videoList
        val flowResult = flowOf(CustomResult.Success(mockVideos))
        Mockito.`when`(playlistRepository.getPlaylist(true)).thenReturn(flowResult)

        val result = getPlaylistUseCase.invoke(GetPlaylistUseCase.Params(withSync = true))

        result.collect { customResult ->
            when (customResult) {
                is CustomResult.Success -> assertEquals(mockVideos, customResult.data)
                else -> fail("Expected a Success result")
            }
        }
    }

    @Test
    fun `should return video list from repository without sync`() = runTest {
        val mockVideos = videoList
        val flowResult = flowOf(CustomResult.Success(mockVideos))
        Mockito.`when`(playlistRepository.getPlaylist(false)).thenReturn(flowResult)

        val result = getPlaylistUseCase.invoke(GetPlaylistUseCase.Params(withSync = false))

        result.collect { customResult ->
            when (customResult) {
                is CustomResult.Success -> assertEquals(mockVideos, customResult.data)
                else -> fail("Expected a Success result")
            }
        }
    }

    @Test
    fun `execute returns error when repository fails`() = runTest {
        val networkIssue = NetworkIssues.SERVER_ERROR
        val flowResult = flowOf(CustomResult.Error<List<Video>>(networkIssue))
        Mockito.`when`(playlistRepository.getPlaylist(true)).thenReturn(flowResult)

        val result = getPlaylistUseCase.invoke(GetPlaylistUseCase.Params(withSync = true))

        result.collect { customResult ->
            assertTrue(customResult is CustomResult.Error)
            assertEquals(networkIssue, customResult.issueType)
        }
    }
}