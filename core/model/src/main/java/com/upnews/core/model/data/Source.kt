package com.upnews.core.model.data

data class Source(
    val id: String,
    val name: String,
    val description: String,
    val category: CategoryType?,
    val language: String,
    val country: String,
    val url: String
)
