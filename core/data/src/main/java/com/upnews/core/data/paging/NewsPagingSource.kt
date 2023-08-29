package com.upnews.core.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.upnews.core.data.repository.NewsResourceQuery
import com.upnews.core.data.util.Response
import com.upnews.core.data.util.getResponse
import com.upnews.core.model.data.Country
import com.upnews.core.network.UpNewsNetworkDataSource
import com.upnews.core.network.model.NetworkNews

class NewsPagingSource(
    private val network: UpNewsNetworkDataSource,
    private val query: NewsResourceQuery,
) : PagingSource<Int, NetworkNews>() {

    override fun getRefreshKey(state: PagingState<Int, NetworkNews>): Int? {
        // We need to get the previous key (or next key if previous is null) of the offset
        // that was closest to the most recently accessed index.
        // Anchor position is the most recently accessed index
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, NetworkNews> {
        val position = params.key ?: 1
        return when (val response = getResponse {
            network.getNewsResources(
                path = query.path.value,
                query = query.query,
                category = query.category?.value,
                country = if (query.country == Country.ALL) null else query.country.code,
                sourceId = query.sourceId,
                page = position,
                pageSize = params.loadSize,
            )
        }) {
            is Response.Success -> {
                val articles = response.data
                if (articles.isNullOrEmpty()) {
                    LoadResult.Page(
                        data = emptyList(),
                        prevKey = null,
                        nextKey = null
                    )
                } else {
                    LoadResult.Page(
                        data = articles,
                        prevKey = if (position == 1) null else position - 1,
                        nextKey = if (articles.isEmpty()) null else position + 1
                    )
                }
            }

            is Response.SuccessButEmpty -> {
                LoadResult.Page(
                    data = emptyList(),
                    prevKey = if (position == 1) null else position - 1,
                    nextKey = null
                )
            }

            is Response.ConnectionError -> LoadResult.Error(Throwable(response.message))
            is Response.ServerError -> LoadResult.Error(Throwable(response.message))
        }
    }
}