package com.upnews.core.network.model

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonNames

@Serializable
data class BaseResponse<T> @OptIn(ExperimentalSerializationApi::class) constructor(
    @SerialName("status") val status: String,
    @SerialName("totalResults") val totalResults: Int? = null,
    @SerialName("message") val message: String? = null,
    @JsonNames("articles", "sources") val data: T? = null,
)