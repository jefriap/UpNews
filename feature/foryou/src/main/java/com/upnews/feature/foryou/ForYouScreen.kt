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

package com.upnews.feature.foryou

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.upnews.core.model.data.CategoryType
import com.upnews.core.model.data.NewsResource
import com.upnews.core.ui.layout.CategoriesLayout
import com.upnews.core.ui.layout.Layout

@Composable
internal fun ForYouRoute(
    onSourceClick: (String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: ForYouViewModel = hiltViewModel(),
) {
    val selectedCategory by viewModel.selectedCategory.collectAsStateWithLifecycle()
    val newsResource = viewModel.newsResources.collectAsLazyPagingItems()
    
    val performChangeSelectedCategory = { category: CategoryType ->
        viewModel.setEvent(ForYouEvent.PerformChangeCategory(category))
    }

    ForYouScreen(
        modifier = modifier,
        selectedCategory = selectedCategory,
        newsResource = newsResource,
        onSelectCategory = performChangeSelectedCategory,
        onSourceClick = onSourceClick,
    )
    
}

@Composable
internal fun ForYouScreen(
    modifier: Modifier = Modifier,
    newsResource: LazyPagingItems<NewsResource>,
    selectedCategory: CategoryType,
    onSelectCategory: (CategoryType) -> Unit,
    onSourceClick: (String) -> Unit,
) {
    val  categories = CategoryType.values().toList()
    
    Column(modifier = modifier.fillMaxWidth()) {
        CategoriesLayout(
            isLoading = newsResource.loadState.refresh is LoadState.Loading,
            categories = categories,
            selectedCategory = selectedCategory,
            onSelectCategory = onSelectCategory,
        )

        newsResource.Layout(
            modifier = Modifier.weight(1f),
            onSourceClick = onSourceClick
        )
    }
    
}
