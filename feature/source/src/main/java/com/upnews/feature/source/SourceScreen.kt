/*
 * Copyright 2021 The Android Open Source Project
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

package com.upnews.feature.source

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.upnews.core.designsystem.component.UpNewsTopAppBar
import com.upnews.core.designsystem.icon.UpNewsIcons
import com.upnews.core.model.data.NewsResource
import com.upnews.core.ui.layout.Layout

@Composable
internal fun SourceRoute(
    onBackClick: () -> Unit,
    onSourceClick: (id: String, name: String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: SourceViewModel = hiltViewModel(),
) {
    val newsResource = viewModel.newsResources.collectAsLazyPagingItems()
    val title = viewModel.sourceArgs.sourceName

    SourceScreen(
        modifier = modifier,
        title = title,
        newsResource = newsResource,
        onSourceClick = onSourceClick,
        onBackClick = onBackClick,
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun SourceScreen(
    modifier: Modifier = Modifier,
    title: String,
    newsResource: LazyPagingItems<NewsResource>,
    onSourceClick: (id: String, name: String) -> Unit,
    onBackClick: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .statusBarsPadding()
    ) {
        UpNewsTopAppBar(
            title = title,
            navigationIcon =  UpNewsIcons.ArrowBack,
            navigationIconContentDescription = stringResource(
                id = com.upnews.core.ui.R.string.back,
            ),
            onNavigationClick = onBackClick,
        )

        newsResource.Layout(
            modifier = Modifier.weight(1f),
            onSourceClick = onSourceClick
        )
    }
}
