package com.upnews.core.ui.component

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.upnews.core.designsystem.icon.UpNewsIcons
import com.upnews.core.ui.R


@Composable
fun SearchToolbar(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit,
    searchQuery: String = "",
    onSearchQueryChanged: (String) -> Unit,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.fillMaxWidth(),
    ) {
        IconButton(onClick = { onBackClick() }) {
            Icon(
                imageVector = UpNewsIcons.ArrowBack,
                contentDescription = stringResource(
                    id = R.string.back,
                ),
            )
        }
        SearchTextField(
            searchQuery = searchQuery,
            onSearchQueryChanged = onSearchQueryChanged,
        )
    }
}