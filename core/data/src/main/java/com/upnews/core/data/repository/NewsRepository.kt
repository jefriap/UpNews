package com.upnews.core.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.map
import com.upnews.core.data.model.asModel
import com.upnews.core.data.paging.NewsPagingSource
import com.upnews.core.network.UpNewsNetworkDataSource
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NewsRepository  @Inject constructor(
    private val upNewsNetworkDataSource: UpNewsNetworkDataSource,
) : NewsRepo {
    override fun getNewsResources(query: NewsResourceQuery) = Pager(
        config = PagingConfig(
            pageSize = 10,
        ),
        pagingSourceFactory ={
            NewsPagingSource(
                network = upNewsNetworkDataSource,
                query = query,
            )
        }
    ).flow.map { pagingData ->
        pagingData.map { it.asModel() }
    }

}