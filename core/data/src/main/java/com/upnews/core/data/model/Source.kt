package com.upnews.core.data.model

import com.upnews.core.model.data.CategoryType
import com.upnews.core.model.data.Source
import com.upnews.core.network.model.NetworkSource

fun NetworkSource.asModel() = Source(
    id = id ?: "",
    name = name ?: "",
    description = description ?: "",
    category = CategoryType.values().find { it.value.contains(category ?: "", true) },
    language = language ?: "",
    country = country ?: "",
    url = url ?: "",
)