/*
 * Copyright 2022 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.upnews.core.network.retrofit

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.upnews.core.network.BuildConfig
import com.upnews.core.network.UpNewsNetworkDataSource
import com.upnews.core.network.model.BaseResponse
import com.upnews.core.network.model.NetworkNews
import kotlinx.serialization.json.Json
import okhttp3.Call
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Retrofit API declaration for Up News Network API
 */
private interface RetrofitUpNewsNetworkApi {

    @GET("{path}")
    suspend fun getNewsResources(
        @Header("Authorization") apiKey: String,
        @Path("path") path: String,
        @Query("q") query: String?,
        @Query("category") category: String?,
        @Query("sources") sourceId: String?,
        @Query("page") page: Int,
        @Query("pageSize") pageSize: Int,
    ): BaseResponse<List<NetworkNews>>

    @GET("top-headlines/source")
    suspend fun getSourceNewsResources(
        @Header("Authorization") apiKey: String,
        @Query("category") category: String?,
        @Query("sources") sources: String,
    ): BaseResponse<List<NetworkNews>>
}

private const val BASE_URL = BuildConfig.BASE_URL
private const val API_KEY = BuildConfig.API_KEY

/**
 * [Retrofit] backed [UpNewsNetworkDataSource]
 */
@Singleton
class RetrofitUpNewsNetwork @Inject constructor(
    networkJson: Json,
    okhttpCallFactory: Call.Factory,
) : UpNewsNetworkDataSource {

    private val networkApi = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .callFactory(okhttpCallFactory)
        .addConverterFactory(
            networkJson.asConverterFactory("application/json".toMediaType()),
        )
        .build()
        .create(RetrofitUpNewsNetworkApi::class.java)

    override suspend fun getNewsResources(
        path: String,
        query: String?,
        category: String?,
        sourceId: String?,
        page: Int,
        pageSize: Int,
    ): BaseResponse<List<NetworkNews>> =
        networkApi.getNewsResources(
            apiKey = "Bearer $API_KEY",
            path = path,
            query = query,
            category = category,
            sourceId = sourceId,
            page = page,
            pageSize = pageSize,
        )

}
