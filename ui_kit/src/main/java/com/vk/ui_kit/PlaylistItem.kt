package com.vk.ui_kit

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import androidx.compose.foundation.layout.size
import androidx.compose.ui.draw.clip
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.Alignment
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import com.vk.resources.theme.LocalDimension
import com.vk.resources.theme.LocalTextDim
import com.vk.resources.theme.VideoPlayerTheme
import com.vk.resources.R

@Composable
fun PlaylistItem(
    modifier: Modifier = Modifier,
    videoItem: VideoItem,
    onClick: (Int) -> Unit = {}
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onClick(videoItem.id) }
            .padding(vertical = LocalDimension.current.spaceSize8),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            modifier = Modifier
                .padding(horizontal = LocalDimension.current.spaceSize4)
                .size(LocalDimension.current.spaceSize58)
                .clip(RoundedCornerShape(LocalDimension.current.spaceSize14))
                .background(MaterialTheme.colorScheme.tertiary),
            contentScale = ContentScale.Crop,
            model = ImageRequest.Builder(LocalContext.current)
                .data(videoItem.previewUrl)
                .crossfade(true)
                .build(),
            placeholder = painterResource(id = R.drawable.ic_placeholder),
            contentDescription = null,
            error = painterResource(id = R.drawable.ic_placeholder),
        )

        Column(modifier = Modifier.padding(horizontal = LocalDimension.current.spaceSize8)) {
            Text(
                text = videoItem.title,
                fontWeight = FontWeight.Bold,
                fontSize = LocalTextDim.current.textSize16,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            Text(
                text = videoItem.duration,
                fontSize = LocalTextDim.current.textSize12,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Preview
@Composable
fun PlayListItemPreview() {
    VideoPlayerTheme {
        PlaylistItem(
            videoItem = VideoItem(
                id = 1,
                title = "YouTube video",
                duration = "24:00",
                previewUrl = null,
                videoUrl = ""
            )
        )
    }
}