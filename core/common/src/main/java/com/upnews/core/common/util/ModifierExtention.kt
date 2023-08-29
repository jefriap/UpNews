package com.upnews.core.common.util

import androidx.annotation.VisibleForTesting
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.placeholder
import com.google.accompanist.placeholder.shimmer


@VisibleForTesting
const val TEST_TAG_LOADING_SHIMMER = "loading_shimmer"

/**
 * Modifier to add shimmer effect
 *
 * @param activate true to activate shimmer effect
 * @param shape shape of the shimmer
 * @param minWidth minimum width of the shimmer
 * @param maxWidth maximum width of the shimmer
 *
 * [minWidth] & [maxWidth] will be ignored if either one of them is 0.dp and use the original width instead
 */
fun Modifier.shimmer(
    activate: Boolean,
    shape: Shape = RoundedCornerShape(2.dp),
    minWidth: Dp = 0.dp,
    maxWidth: Dp = 0.dp,
): Modifier {
    val placeHolder = this
        .placeholder(
            visible = activate,
            highlight = PlaceholderHighlight.shimmer(highlightColor = Color.White),
            color = Color.LightGray.copy(0.5f),
            shape = shape,
        )
        .testTag(TEST_TAG_LOADING_SHIMMER)

    return placeHolder.width(
        generateRandomWidth(
            minWidth = minWidth,
            maxWidth = maxWidth,
        ),
    ).takeIf { minWidth > 0.dp && maxWidth > minWidth && activate }
        ?: placeHolder.takeIf { activate } ?: this
}

private fun generateRandomWidth(
    minWidth: Dp,
    maxWidth: Dp,
): Dp {
    // Generate random width between min and max
    val random = Math.random() * (maxWidth.value - minWidth.value) + minWidth.value
    return random.dp
}

fun Modifier.onClickRippleOff(enabled: Boolean = true, onClick: () -> Unit): Modifier = composed {
    clickable(
        onClick = onClick,
        indication = null,
        interactionSource = remember { MutableInteractionSource() },
        enabled = enabled
    )
}