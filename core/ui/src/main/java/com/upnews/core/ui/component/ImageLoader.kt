package com.upnews.core.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.BrokenImage
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import coil.compose.rememberAsyncImagePainter
import com.upnews.core.ui.util.shimmer

@Composable
fun ImageLoader(
    modifier: Modifier = Modifier,
    size: Dp? = null,
    url: String,
    isLoading: Boolean = false,
    errorMessage: String = "",
    errorIcon: ImageVector = Icons.Rounded.BrokenImage,
    contentScale: ContentScale = ContentScale.Fit,
    alignment: Alignment = Alignment.Center,
    colorErrorIcon: Color = Color.LightGray,
    loadingContent: @Composable () -> Unit = {
        Image(
            painter = rememberAsyncImagePainter(model = ""), contentDescription = "",
            modifier = modifier.shimmer(true)
        )
    },
    errorContent: (@Composable (String, Modifier) -> Unit)? = null,
) {
    SubcomposeAsyncImage(
        model = url,
        contentDescription = null,
        modifier = modifier.apply {
            if (size != null) {
                size(size)
            }
        },
        contentScale = contentScale,
        alignment = alignment,
    ) {
        val state = painter.state
        when {
            state is AsyncImagePainter.State.Loading || isLoading -> {
                loadingContent()
            }

            state is AsyncImagePainter.State.Error -> {
                if (errorContent != null) {
                    errorContent(state.result.throwable.message ?: "unknown error", modifier)
                } else {
                    ImagePlaceHolder(
                        color = colorErrorIcon,
                        icon = errorIcon,
                        desc = errorMessage.ifEmpty {
                            state.result.throwable.message ?: "unknown error"
                        },
                        size = size
                    )
                }

            }

            else -> SubcomposeAsyncImageContent()

        }
    }
}

@Composable
fun ImagePlaceHolder(
    modifier: Modifier = Modifier,
    color: Color,
    icon: ImageVector,
    iconDesc: String = "",
    size: Dp? = null,
    desc: String,
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally, modifier = modifier
    ) {
        Icon(
            imageVector = icon,
            contentDescription = iconDesc,
            tint = color,
            modifier = modifier.apply {
                if (size != null) {
                    size(size)
                }
            }
        )
        Text(
            text = desc, style = MaterialTheme.typography.bodyMedium.copy(
                color = color, fontWeight = FontWeight.Medium,
                textAlign = TextAlign.Center
            )
        )
    }
}