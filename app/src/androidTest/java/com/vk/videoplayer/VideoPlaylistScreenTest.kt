package com.vk.videoplayer

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.vk.domain.model.Video
import com.vk.feature_playlist.VideoPlaylistScreen
import org.junit.Rule
import org.junit.Test
import com.vk.resources.R
import junit.framework.TestCase.assertEquals
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class VideoPlaylistScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testErrorState() {
        composeTestRule.setContent {
            VideoPlaylistScreen(isLoading = false, errorStringRes = R.string.no_internet_error)
        }
        composeTestRule.onNodeWithText("No internet. Please, connect").assertIsDisplayed()
    }

    @Test
    fun testVideoListDisplayed() {
        val mockVideos = listOf(
            Video(id = 1, title = "Video 1", duration = "3:00", previewUrl = "", videoUrl = "url1"),
            Video(id = 2, title = "Video 2", duration = "4:00", previewUrl = "", videoUrl = "url2")
        )

        composeTestRule.setContent {
            VideoPlaylistScreen(isLoading = false, videoList = mockVideos)
        }

        composeTestRule.onNodeWithText("Video 1").assertIsDisplayed()
        composeTestRule.onNodeWithText("Video 2").assertIsDisplayed()
    }

    @Test
    fun testItemClick() {
        val mockVideos = listOf(
            Video(id = 1, title = "Video 1", duration = "3:00", previewUrl = "", videoUrl = "url1")
        )

        var clickedUrl: String? = null

        composeTestRule.setContent {
            VideoPlaylistScreen(
                isLoading = false,
                videoList = mockVideos,
                onItemClick = { clickedUrl = it }
            )
        }

        composeTestRule.onNodeWithText("Video 1").performClick()
        assertEquals("url1", clickedUrl)
    }
}