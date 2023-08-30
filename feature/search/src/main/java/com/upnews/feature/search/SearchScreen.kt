/*
 * Copyright 2023 The Android Open Source Project
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

package com.upnews.feature.search

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.upnews.core.designsystem.icon.UpNewsIcons
import com.upnews.core.model.data.NewsResource
import com.upnews.core.ui.component.SearchTextField
import com.upnews.core.ui.component.SearchToolbar
import com.upnews.core.ui.layout.Layout
import com.upnews.core.ui.R as UiR

@Composable
internal fun SearchRoute(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit,
    onSourceClick: (id: String, name: String) -> Unit,
    searchViewModel: SearchViewModel = hiltViewModel(),
) {
    val searchQuery by searchViewModel.searchQuery.collectAsStateWithLifecycle()
    val newsResources = searchViewModel.newsResources.collectAsLazyPagingItems()

    SearchScreen(
        modifier = modifier,
        newsResources = newsResources,
        onBackClick = onBackClick,
        searchQuery = searchQuery,
        onSearchQueryChanged = searchViewModel::onSearchQueryChanged,
        onSourceClick = onSourceClick,
    )

}

@Composable
internal fun SearchScreen(
    modifier: Modifier = Modifier,
    newsResources: LazyPagingItems<NewsResource>,
    onBackClick: () -> Unit,
    searchQuery: String,
    onSearchQueryChanged: (String) -> Unit,
    onSourceClick: (id: String, name: String) -> Unit
) {
    Column(modifier = modifier
        .fillMaxSize()
        .statusBarsPadding() ) {
        SearchToolbar(
            onBackClick = onBackClick,
            onSearchQueryChanged = onSearchQueryChanged,
            searchQuery = searchQuery,
        )

        newsResources.Layout(modifier = Modifier.weight(1f), onSourceClick = onSourceClick)
    }
}