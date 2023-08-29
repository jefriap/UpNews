package com.upnews.core.data.repository

import com.upnews.core.common.result.Result
import com.upnews.core.data.model.asModel
import com.upnews.core.data.util.Response
import com.upnews.core.data.util.getResponse
import com.upnews.core.model.data.Country
import com.upnews.core.model.data.Source
import com.upnews.core.network.UpNewsNetworkDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SourcesRepository @Inject constructor(
    private val upNewsNetworkDataSource: UpNewsNetworkDataSource,
) : SourcesRepo {

    override suspend fun getSources(query: SourcesQuery): Flow<Result<List<Source>>> = flow {
        emit(
            when (val response = getResponse {
                upNewsNetworkDataSource.getSources(
                    category = query.category?.value,
                    country = if (query.country == Country.ALL) null else query.country.code,
                )
            }) {
                is Response.ConnectionError -> Result.Error(response.message)
                is Response.ServerError -> Result.Error(response.message)
                is Response.Success -> Result.Success(response.data?.map { it.asModel() })
                is Response.SuccessButEmpty -> Result.Success(emptyList())
            }
        )
    }.onStart { emit(Result.Loading()) }

}