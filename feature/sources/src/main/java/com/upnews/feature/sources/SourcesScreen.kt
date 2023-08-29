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

package com.upnews.feature.sources

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.upnews.core.common.R
import com.upnews.core.common.util.DummyData
import com.upnews.core.model.data.CategoryType
import com.upnews.core.model.data.Source
import com.upnews.core.ui.card.CardItem
import com.upnews.core.ui.layout.CategoriesLayout
import com.upnews.core.ui.layout.LayoutIllustration

@Composable
internal fun SourcesRoute(
    onSourceClick: (id: String, name: String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: SourcesViewModel = hiltViewModel(),
) {
    val selectedCategory by viewModel.selectedCategory.collectAsStateWithLifecycle()
    val sourcesUIState by viewModel.sources.collectAsStateWithLifecycle()

    val performChangeSelectedCategory = { category: CategoryType? ->
        viewModel.setEvent(SourcesEvent.PerformChangeCategory(category))
    }

    SourcesScreen(
        modifier = modifier,
        sourcesUiState = sourcesUIState,
        selectedCategory = selectedCategory,
        onSelectCategory = performChangeSelectedCategory,
        onSourceClick = onSourceClick,
    )
}

@Composable
internal fun SourcesScreen(
    modifier: Modifier = Modifier,
    sourcesUiState: SourcesUiState,
    selectedCategory: CategoryType?,
    onSelectCategory: (CategoryType?) -> Unit,
    onSourceClick: (id: String, name: String) -> Unit,
) {
    val categories = CategoryType.values().toList()

    Column(modifier = modifier.fillMaxWidth()) {
        CategoriesLayout(
            isLoading = sourcesUiState is SourcesUiState.Loading,
            categories = categories,
            selectedCategory = selectedCategory,
            onSelectCategory = onSelectCategory,
            onSelectAll = { onSelectCategory(null) },
        )

        Box(modifier = Modifier.weight(1f)) {
            when (sourcesUiState) {
                is SourcesUiState.Error -> {
                    LayoutIllustration(
                        title = stringResource(id = R.string.cannot_load),
                        desc = sourcesUiState.message,
                        onClickAction = { },
                        image = com.upnews.core.ui.R.drawable.ic_face_persevering,
                    )
                }

                SourcesUiState.Loading -> {
                     DummyData.sources.Layout(modifier = modifier.fillMaxSize(), isLoading = true)
                }
                is SourcesUiState.Success -> {
                    sourcesUiState.sources.Layout(
                        modifier = modifier.fillMaxSize(),
                        onSourceClick = onSourceClick,
                    )
                }
            }
        }
    }

}

@Composable
internal fun List<Source>.Layout(
    modifier: Modifier = Modifier,
    isLoading: Boolean = false,
    onSourceClick: (id: String, name: String) -> Unit = {_,_ ->},
) {
    val scrollState = rememberLazyListState()

    if (this@Layout.isNotEmpty()) {
        LazyColumn(
            modifier = modifier,
            state = scrollState,
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            if (!isLoading) {
                items(this@Layout) { source ->
                    source.CardItem(
                        onClick = onSourceClick,
                    )
                }
            }
            if (isLoading) {
                items(this@Layout) { item ->
                    item.CardItem(
                        isLoading = true,
                    )
                }
            }
        }
    } else {
        LayoutIllustration(
            title = stringResource(id = R.string.not_found),
            desc = stringResource(id = R.string.not_found_desc),
            image = com.upnews.core.ui.R.drawable.ic_lightbulb_question,
        )
    }
}