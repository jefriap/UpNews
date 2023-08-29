/*
 * Copyright 2022 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.upnews.core.ui.card

import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.onClick
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import com.upnews.core.common.R.*
import com.upnews.core.common.util.PreviewParameterProvider
import com.upnews.core.common.util.getFormatDate
import com.upnews.core.common.util.launchCustomChromeTab
import com.upnews.core.common.util.shimmer
import com.upnews.core.designsystem.component.UpNewsIconToggleButton
import com.upnews.core.designsystem.icon.UpNewsIcons
import com.upnews.core.designsystem.theme.UpNewsTheme
import com.upnews.core.model.data.NewsResource
import com.upnews.core.ui.R
import com.upnews.core.ui.component.ImageLoader

/**
 * [NewsResource] card used on the following screens: For You, Saved
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewsResource.CardItem(
    modifier: Modifier = Modifier,
    isLoading: Boolean = false,
    isBookmarked: Boolean = false,
    onToggleBookmark: (() -> Unit)? = null,
    onSourceClick: (id: String, name: String) -> Unit = { _, _ -> },
) {
    val clickActionLabel = stringResource(R.string.card_tap_action)
    val context = LocalContext.current
    val backgroundColor = MaterialTheme.colorScheme.background.toArgb()
    val resourceUrl by remember {
        mutableStateOf(Uri.parse(url))
    }

    Card(
        onClick = {
            launchCustomChromeTab(context, resourceUrl, backgroundColor)
        },
        enabled = !isLoading,
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(2.dp),
        // Use custom label for accessibility services to communicate button's action to user.
        // Pass null for action to only override the label and not the actual action.
        modifier = modifier.semantics {
            onClick(label = clickActionLabel, action = null)
        },
    ) {
        Column {
            Row {
                ImageLoader(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(180.dp),
                    url = urlToImage,
                    isLoading = isLoading,
                    errorMessage = stringResource(
                        string.cannot_load_desc, stringResource(
                            id = string.image
                        )
                    ),
                    contentScale = ContentScale.FillBounds
                )
            }
            Box(
                modifier = Modifier.padding(16.dp),
            ) {
                Column {
                    Spacer(modifier = Modifier.height(12.dp))
                    Row {
                        NewsResourceTitle(
                            title,
                            modifier = Modifier
                                .weight(.8f)
                                .shimmer(isLoading),
                        )
                        if (onToggleBookmark != null && !isLoading) {
                            BookmarkButton(isBookmarked, onToggleBookmark)
                        }
                    }
                    Spacer(modifier = Modifier.height(12.dp))
                    NewsResourceMetaData(
                        isLoading,
                        publishedAt,
                        author,
                        sourceName,
                        onSourceClick = {
                            onSourceClick(sourceId, sourceName)
                        },
                        enabled = sourceId.isNotBlank()
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    NewsResourceShortDescription(isLoading, description)
                }
            }
        }
    }
}

@Composable
fun NewsResourceTitle(
    newsResourceTitle: String,
    modifier: Modifier = Modifier,
) {
    Text(
        newsResourceTitle, style = MaterialTheme.typography.headlineSmall, modifier = modifier
    )
}

@Composable
fun BookmarkButton(
    isBookmarked: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    UpNewsIconToggleButton(
        checked = isBookmarked,
        onCheckedChange = { onClick() },
        modifier = modifier,
        icon = {
            Icon(
                imageVector = UpNewsIcons.BookmarkBorder,
                contentDescription = stringResource(R.string.bookmark),
            )
        },
        checkedIcon = {
            Icon(
                imageVector = UpNewsIcons.Bookmark,
                contentDescription = stringResource(R.string.unbookmark),
            )
        },
    )
}

@Composable
fun NewsResourceMetaData(
    isLoading: Boolean,
    publishDate: String,
    author: String,
    sourceName: String,
    onSourceClick: () -> Unit,
    enabled: Boolean,
) {
    val formattedDate = publishDate.take(10).getFormatDate(
        oldPattern = "yyyy-MM-dd",
    )
    Text(
        text = if (sourceName.isNotBlank()) {
            stringResource(R.string.card_meta_data_text, formattedDate, author)
        } else {
            formattedDate
        },
        modifier = Modifier.shimmer(isLoading),
        style = MaterialTheme.typography.labelSmall,
    )
    if (sourceName.isNotBlank()) {
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = sourceName,
            modifier = Modifier
                .shimmer(isLoading)
                .clickable(enabled) { onSourceClick() },
            style = MaterialTheme.typography.labelMedium.copy(
                color = if (enabled) Color.Blue else Color.Unspecified
            ),
        )
    }
}

@Composable
fun NewsResourceShortDescription(
    isLoading: Boolean,
    newsResourceShortDescription: String,
) {
    if (isLoading) {
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            repeat(4) {
                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(20.dp)
                        .shimmer(true)
                )
            }
        }
    } else {
        Text(
            text = newsResourceShortDescription,
            style = MaterialTheme.typography.bodyLarge
        )
    }
}

@Preview("Bookmark Button")
@Composable
private fun BookmarkButtonPreview() {
    UpNewsTheme {
        Surface {
            BookmarkButton(isBookmarked = false, onClick = { })
        }
    }
}

@Preview("Bookmark Button Bookmarked")
@Composable
private fun BookmarkButtonBookmarkedPreview() {
    UpNewsTheme {
        Surface {
            BookmarkButton(isBookmarked = true, onClick = { })
        }
    }
}

@Preview("NewsResourceCardExpanded")
@Composable
private fun ExpandedNewsResourcePreview(
    @PreviewParameter(PreviewParameterProvider::class)
    newsResources: List<NewsResource>,
) {
    CompositionLocalProvider(
        LocalInspectionMode provides true,
    ) {
        UpNewsTheme {
            Surface {
                newsResources[0].CardItem(
                    isBookmarked = false,
                    onToggleBookmark = {},
                    onSourceClick = {_, _ ->}
                )
            }
        }
    }
}
