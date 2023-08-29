package com.upnews.core.data.repository

import com.upnews.core.common.result.Result
import com.upnews.core.model.data.CategoryType
import com.upnews.core.model.data.Country
import com.upnews.core.model.data.Source
import kotlinx.coroutines.flow.Flow

data class SourcesQuery(
    val category: CategoryType? = null,
    val country: Country,
)

interface SourcesRepo {
    suspend fun getSources(
        query: SourcesQuery,
    ): Flow<Result<List<Source>>>
}