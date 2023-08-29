package com.upnews.core.ui.layout

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.upnews.core.common.R
import com.upnews.core.model.data.CategoryType


@Composable
fun CategoriesLayout(
    isLoading: Boolean,
    categories: List<CategoryType>,
    selectedCategory: CategoryType?,
    onSelectCategory: (CategoryType) -> Unit,
    onSelectAll: (() -> Unit)? = null,
) {
    LazyRow(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalAlignment = Alignment.CenterVertically,
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
    ) {
        if (onSelectAll != null) {
            item {
                Item(
                    isLoading = isLoading,
                    isSelected = selectedCategory == null,
                    title = R.string.all,
                    onClick = { onSelectAll() },
                )
            }
        }
        items(categories) { category ->
            Item(
                isLoading = isLoading,
                isSelected = category == selectedCategory,
                title = when (category) {
                    CategoryType.GENERAL -> R.string.general
                    CategoryType.TECHNOLOGY -> R.string.technology
                    CategoryType.BUSINESS -> R.string.business
                    CategoryType.ENTERTAINMENT -> R.string.entertainment
                    CategoryType.HEALTH -> R.string.health
                    CategoryType.SCIENCE -> R.string.science
                    CategoryType.SPORTS -> R.string.sports
                },
                onClick = { onSelectCategory(category) },
            )
        }
    }
}

@Composable
private fun Item(
    isLoading: Boolean,
    isSelected: Boolean,
    @StringRes title: Int,
    onClick: () -> Unit,
) {
    AssistChip(
        onClick = onClick,
        enabled = !isLoading,
        colors = AssistChipDefaults.assistChipColors(
            containerColor = if (isSelected) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.surface,
        ),
        border = AssistChipDefaults.assistChipBorder(
            borderColor = MaterialTheme.colorScheme.onBackground,
            borderWidth = 1.dp
        ),
        shape = MaterialTheme.shapes.small,
        label = {
            Text(
                text = stringResource(id = title),
                style = MaterialTheme.typography.labelMedium,
            )
        },
    )
}