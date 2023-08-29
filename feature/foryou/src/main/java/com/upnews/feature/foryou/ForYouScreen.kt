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

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults.assistChipBorder
import androidx.compose.material3.AssistChipDefaults.assistChipColors
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.upnews.core.common.R
import com.upnews.core.model.data.CategoryType
import com.upnews.core.model.data.NewsResource
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
        onItemClick = {},
        onSourceClick = onSourceClick,
    )
    
}

@Composable
internal fun ForYouScreen(
    modifier: Modifier = Modifier,
    newsResource: LazyPagingItems<NewsResource>,
    selectedCategory: CategoryType,
    onSelectCategory: (CategoryType) -> Unit,
    onItemClick: (NewsResource) -> Unit,
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
            onClickItem = onItemClick,
            onSourceClick = onSourceClick
        )
    }
    
}

@Composable
internal fun CategoriesLayout(
    isLoading: Boolean,
    categories: List<CategoryType>,
    selectedCategory: CategoryType,
    onSelectCategory: (CategoryType) -> Unit,
) {
    LazyRow(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
    ) {
        items(categories) { category ->
            category.Item(
                isLoading = isLoading,
                isSelected = category == selectedCategory,
                onClick = { onSelectCategory(category) }
            )
        }
    }
}

@Composable
private fun CategoryType.Item(
    isLoading: Boolean,
    isSelected: Boolean,
    onClick: () -> Unit,
) {
    AssistChip(
        onClick = onClick,
        enabled = !isLoading,
        colors = assistChipColors(
            containerColor = if (isSelected) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.surface,
        ),
        border = assistChipBorder(
            borderColor = MaterialTheme.colorScheme.onBackground,
            borderWidth = 1.dp
        ),
        shape = MaterialTheme.shapes.small,
        label = {
            Text(
                text = stringResource(id = when (this@Item) {
                    CategoryType.GENERAL -> R.string.general
                    CategoryType.TECHNOLOGY -> R.string.technology
                    CategoryType.BUSINESS -> R.string.business
                    CategoryType.ENTERTAINMENT -> R.string.entertainment
                    CategoryType.HEALTH -> R.string.health
                    CategoryType.SCIENCE -> R.string.science
                    CategoryType.SPORTS -> R.string.sports
                }
                ),
                style = MaterialTheme.typography.labelMedium,
            )
        },
        trailingIcon = if (isSelected) {
            {
                Icon(
                    imageVector = Icons.Outlined.Check,
                    contentDescription = "Selected Category",
                    modifier = Modifier.size(24.dp),
                )
            }
        } else null
    )
}
