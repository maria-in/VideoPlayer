package com.vk.videoplayer

import androidx.activity.compose.BackHandler
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.vk.feature_playlist.VideoPlaylistScreen

@Composable
fun Navigation(modifier: Modifier = Modifier) {
    val navController = rememberNavController()

    BackHandler(enabled = navController.currentBackStackEntry != null) {
        navController.navigateUp()
    }

    NavHost(navController, startDestination = "playlist_screen") {
        composable(
            route = "playlist_screen",
            enterTransition = { fadeIn(animationSpec = tween(500)) }
        ) {

            VideoPlaylistScreen(
                modifier = modifier
            )
        }
    }
}