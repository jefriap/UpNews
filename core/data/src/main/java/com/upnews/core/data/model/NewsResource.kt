package com.upnews.core.data.model

import com.upnews.core.model.data.NewsResource
import com.upnews.core.network.model.NetworkNews

fun NetworkNews.asModel() = NewsResource(
    sourceId = source.id ?: "",
    sourceName = source.name ?: "",
    author = author ?: "",
    title = title ?: "",
    description = description ?: "",
    url = url ?: "",
    urlToImage = urlToImage ?: "",
    publishedAt = publishedAt ?: "",
    content = content ?: "",
)