package com.upnews.feature.sources

import com.upnews.core.model.data.CategoryType

sealed interface SourcesEvent {
    data class PerformChangeCategory(val category: CategoryType?) : SourcesEvent
}