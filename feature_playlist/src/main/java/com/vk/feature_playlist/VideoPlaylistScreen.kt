package com.vk.feature_playlist

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import com.vk.domain.model.Video
import com.vk.resources.theme.VideoPlayerTheme
import com.vk.ui_kit.PlaylistItem
import com.vk.ui_kit.VideoItem
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import com.vk.resources.R
import com.vk.resources.theme.LocalDimension

@Composable
fun VideoPlaylistScreen(
    modifier: Modifier = Modifier,
    viewModel: VideoPlaylistViewModel,
    navController: NavController
) {
    val state by viewModel.uiState.collectAsState()
    val scope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        viewModel.effect.onEach {
            when(it) {
                is VideoPlaylistContract.Effect.NavigateToVideoScreen -> {
                    navController.navigate("player_screen")
                }
            }
        }.launchIn(scope)
    }

    VideoPlaylistScreen(
        modifier = modifier,
        isLoading = state.isLoading,
        videoList = state.videoList,
        onItemClick = { videoUrl ->
            viewModel.handleEvent(VideoPlaylistContract.Event.OnVideoClicked(videoUrl))
        },
        onReload = { viewModel.handleEvent(VideoPlaylistContract.Event.OnReload) }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VideoPlaylistScreen(
    modifier: Modifier = Modifier,
    isLoading: Boolean,
    videoList: List<Video> = listOf(),
    errorStringRes: Int? = null,
    onItemClick: (String) -> Unit = {},
    onReload: () -> Unit = {}
) {
    val pullToRefreshState = rememberPullToRefreshState()

    PullToRefreshBox(
        modifier = modifier.fillMaxSize(),
        state = pullToRefreshState,
        isRefreshing = isLoading,
        onRefresh = onReload,
    ) {
        if (errorStringRes != null) {
            Column(
                modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(text = stringResource(errorStringRes))
            }
        } else if(videoList.isEmpty()) {
            Column(
                modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(text = stringResource(R.string.empty_list_error))
            }
        } else {
            LazyColumn(Modifier.fillMaxSize()) {
                items(videoList) { item ->

                    PlaylistItem(
                        modifier = Modifier.padding(horizontal = LocalDimension.current.spaceSize12),
                        videoItem = VideoItem(
                            id = item.id,
                            title = item.title,
                            duration = item.duration,
                            previewUrl = item.previewUrl,
                            videoUrl = item.videoUrl
                        ),
                        onClick = { onItemClick(item.videoUrl) }
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun VideoPlaylistScreenListPreview() {
    VideoPlayerTheme {
        VideoPlaylistScreen(
            isLoading = true,
            videoList = listOf(
                Video(
                    id = 0,
                    title = "Some video with long name",
                    duration = "00:52",
                    previewUrl = "",
                    videoUrl = ""
                )
            ),
        )
    }
}

@Preview
@Composable
fun VideoPlaylistScreenErrorPreview() {
    VideoPlayerTheme {
        VideoPlaylistScreen(
            isLoading = false,
            errorStringRes = R.string.no_internet_error
        )
    }
}