package com.upnews.feature.foryou

import com.upnews.core.model.data.CategoryType

sealed interface ForYouEvent {
    data class PerformChangeCategory(val category: CategoryType) : ForYouEvent
}