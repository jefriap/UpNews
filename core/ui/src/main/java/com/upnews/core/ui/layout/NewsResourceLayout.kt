package com.upnews.core.ui.layout

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.upnews.core.common.util.DummyData
import com.upnews.core.model.data.NewsResource
import com.upnews.core.ui.R
import com.upnews.core.ui.card.CardItem
import com.upnews.core.common.R as RCommon

@Composable
fun LazyPagingItems<NewsResource>.Layout(
    modifier: Modifier = Modifier,
    onSourceClick: (id: String, name: String) -> Unit,
) {

    val scrollState = rememberLazyListState()
    val isError = loadState.refresh is LoadState.Error
    val isEmpty = itemCount == 0
    val isLoading = loadState.refresh is LoadState.Loading
    val isLoadingAppend = loadState.append is LoadState.Loading

    val dummy = DummyData.newsResources

    Box(modifier = modifier) {
        when {
            isEmpty and !isLoading and !isError -> {
                LayoutIllustration(
                    title = stringResource(id = RCommon.string.not_found),
                    desc = (loadState.refresh as? LoadState.Error)?.error?.message
                        ?: stringResource(id = RCommon.string.not_found_desc),
                    onClickAction = { refresh() },
                    image = R.drawable.ic_lightbulb_question,
                )
            }

            isError -> {
                LayoutIllustration(
                    title = stringResource(id = RCommon.string.cannot_load),
                    desc = (loadState.refresh as? LoadState.Error)?.error?.message
                        ?: stringResource(id = RCommon.string.cannot_load_desc, "Berita"),
                    onClickAction = { refresh() },
                    image = R.drawable.ic_face_persevering,
                )
            }

            else -> {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    state = scrollState,
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                ) {
                    if (!isLoading) {
                        items(
                            itemCount
                        ) { index ->
                            val item = this@Layout[index]
                            item?.CardItem(
                                onSourceClick = onSourceClick,
                            )
                        }
                    }
                    if (isLoading || isLoadingAppend) {
                        items(dummy) { item ->
                            item.CardItem(
                                isLoading = true,
                            )
                        }
                    }
                }
            }
        }
    }
}