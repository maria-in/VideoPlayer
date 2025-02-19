package com.vk.feature_playlist

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.vk.resources.theme.VideoPlayerTheme
import com.vk.ui_kit.PlaylistItem
import com.vk.ui_kit.VideoItem
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VideoPlaylistScreen(
    modifier: Modifier = Modifier
) {

    var itemCount by remember { mutableIntStateOf(15) }
    var isRefreshing by remember { mutableStateOf(false) }
    val state = rememberPullToRefreshState()
    val coroutineScope = rememberCoroutineScope()
    val onRefresh: () -> Unit = {
        isRefreshing = true
        coroutineScope.launch {
            delay(5000)
            itemCount += 5
            isRefreshing = false
        }
    }

    PullToRefreshBox(
        modifier = modifier,
        state = state,
        isRefreshing = isRefreshing,
        onRefresh = onRefresh,
    ) {
        LazyColumn(Modifier.fillMaxSize()) {
            items(itemCount) {
                PlaylistItem(
                    videoItem = VideoItem(id = 1L, title = "Video name", duration = "00:20")
                )
            }
        }
    }
}

@Preview
@Composable
fun VideoPlaylistScreenPreview() {
    VideoPlayerTheme {
        VideoPlaylistScreen()
    }
}