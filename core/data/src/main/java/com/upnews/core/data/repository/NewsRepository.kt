package com.upnews.core.data.repository

import com.upnews.core.network.UpNewsNetworkDataSource
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NewsRepository  @Inject constructor(
    private val upNewsNetworkDataSource: UpNewsNetworkDataSource,
) : NewsRepo {
//    override fun getNewsResources(query: NewsResourceQuery) = Pager(
//        config = PagingConfig(
//            pageSize = 10,
//        ),
//        pagingSourceFactory ={
//            NewsPagingSource(
//                network = upNewsNetworkDataSource,
//                query = query,
//            )
//        }
//    ).flow.map { pagingData ->
//        pagingData.map { it.asModel() }
//    }
}