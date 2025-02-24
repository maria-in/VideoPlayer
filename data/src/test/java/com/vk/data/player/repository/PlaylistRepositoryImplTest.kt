package com.vk.data.player.repository

import com.vk.data.player.datasource.local.dao.VideoPlayerDao
import com.vk.data.player.datasource.local.model.VideoItemEntity
import com.vk.data.player.datasource.remote.api.VideoApi
import com.vk.data.player.datasource.remote.models.VideoDto
import com.vk.data.player.mappers.toVideo
import com.vk.data.player.mappers.toVideoEntity
import com.vk.domain.utils.CustomResult
import com.vk.domain.utils.NetworkIssues
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertTrue
import org.junit.Assert.fail
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.never
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

class PlaylistRepositoryImplTest {

    private var videoApi = mock<VideoApi>()
    private var localSource = mock<VideoPlayerDao>()
    private lateinit var repository: PlaylistRepositoryImpl

    @Before
    fun setup() {
        repository = PlaylistRepositoryImpl(videoApi, localSource)
    }

    @Test
    fun `getPlaylist should return success when API call is successful`() = runTest {
        val mockDtoList = listOf(
            VideoDto(
                id = 1,
                title = "Video 1",
                thumbnailUrl = "http://example.com/thumbnail1.jpg",
                duration = "3:00",
                uploadTime = "2021-01-01",
                views = "1000",
                author = "Author 1",
                videoUrl = "http://example.com/video1.mp4",
                description = "Description 1",
                subscriber = "10",
                isLive = false
            ),
            VideoDto(
                id = 2,
                title = "Video 2",
                thumbnailUrl = "http://example.com/thumbnail2.jpg",
                duration = "4:00",
                uploadTime = "2021-01-02",
                views = "2000",
                author = "Author 2",
                videoUrl = "http://example.com/video2.mp4",
                description = "Description 2",
                subscriber = "20",
                isLive = false
            )
        )

        val mockVideoList = mockDtoList.map { it.toVideoEntity() }

        whenever(videoApi.getVideos()).thenReturn(Result.success(mockDtoList))
        whenever(localSource.getVideoPlaylist()).thenReturn(mockVideoList)

        val resultFlow = repository.getPlaylist(withSync = true)

        resultFlow.collect { result ->
            assert(result is CustomResult.Success)
            when (result) {
                is CustomResult.Success -> assertEquals(mockDtoList.map { it.toVideo() }, result.data)
                else -> fail("Expected a Success result")
            }
        }
        verify(localSource).clearCache()
        verify(localSource).saveVideoPlaylist(mockVideoList)
    }

    @Test
    fun `getPlaylist should return error when API call fails`() = runTest {
        val throwable = Throwable("Network error")
        whenever(videoApi.getVideos()).thenReturn(Result.failure(throwable))
        whenever(localSource.getVideoPlaylist()).thenReturn(emptyList())

        val resultFlow = repository.getPlaylist(withSync = true)

        resultFlow.collect { result ->
            assertTrue(result is CustomResult.Error)
            assertTrue((result as CustomResult.Error).issueType == NetworkIssues.UNKNOWN_ERROR)
        }

        verify(localSource, never()).clearCache()
        verify(localSource, never()).saveVideoPlaylist(any())
    }

    @Test
    fun `getPlaylist should return local data when not syncing`() = runTest {
        val mockVideoItemEntities = listOf(
            VideoItemEntity(
                1,
                "Video 1",
                "3:00",
                "http://example.com/thumbnail1.jpg",
                "http://example.com/video1.mp4"
            )
        )

        whenever(localSource.getVideoPlaylist()).thenReturn(mockVideoItemEntities)
        val resultFlow = repository.getPlaylist(withSync = false)

        resultFlow.collect { result ->
            assert(result is CustomResult.Success)
            when (result) {
                is CustomResult.Success -> assertEquals(mockVideoItemEntities.map { it.toVideo() }, result.data)
                else -> fail("Expected a Success result")
            }
        }
        verify(videoApi, never()).getVideos()
    }
}