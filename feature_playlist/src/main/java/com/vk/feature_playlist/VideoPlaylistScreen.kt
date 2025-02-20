package com.vk.feature_playlist

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.vk.resources.theme.VideoPlayerTheme
import com.vk.ui_kit.PlaylistItem
import com.vk.ui_kit.VideoItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VideoPlaylistScreen(
    modifier: Modifier = Modifier,
    viewModel: VideoPlaylistViewModel
) {

    val state by viewModel.uiState.collectAsState()
    val pullToRefreshState = rememberPullToRefreshState()

    PullToRefreshBox(
        modifier = modifier,
        state = pullToRefreshState,
        isRefreshing = state.isLoading,
        onRefresh = {},
    ) {
        LazyColumn(Modifier.fillMaxSize()) {
            items(state.videoList) { item ->

                PlaylistItem(
                    videoItem = VideoItem(id = item.id, title = item.title, duration = item.duration)
                )
            }
        }
    }
}

@Preview
@Composable
fun VideoPlaylistScreenPreview() {
    VideoPlayerTheme {
        //VideoPlaylistScreen()
    }
}