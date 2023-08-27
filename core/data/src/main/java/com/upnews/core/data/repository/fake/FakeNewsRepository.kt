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

package com.upnews.core.data.repository.fake

import com.upnews.core.data.Synchronizer
import com.upnews.core.data.model.asEntity
import com.upnews.core.data.repository.NewsRepository
import com.upnews.core.data.repository.NewsResourceQuery
import com.upnews.core.database.model.NewsResourceEntity
import com.upnews.core.database.model.asExternalModel
import com.upnews.core.model.data.NewsResource
import com.upnews.core.common.network.Dispatcher
import com.upnews.core.common.network.UpNewsDispatchers.IO
import com.upnews.core.network.fake.FakeUpNewsNetworkDataSource
import com.upnews.core.network.model.NetworkNewsResource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

/**
 * Fake implementation of the [NewsRepository] that retrieves the news resources from a JSON String.
 *
 * This allows us to run the app with fake data, without needing an internet connection or working
 * backend.
 */
class FakeNewsRepository @Inject constructor(
    @Dispatcher(IO) private val ioDispatcher: CoroutineDispatcher,
    private val datasource: FakeUpNewsNetworkDataSource,
) : NewsRepository {

    override fun getNewsResources(
        query: NewsResourceQuery,
    ): Flow<List<NewsResource>> =
        flow {
            emit(
                datasource
                    .getNewsResources()
                    .filter { networkNewsResource ->
                        // Filter out any news resources which don't match the current query.
                        // If no query parameters (filterTopicIds or filterNewsIds) are specified
                        // then the news resource is returned.
                        listOfNotNull(
                            true,
                            query.filterNewsIds?.contains(networkNewsResource.id),
                            query.filterTopicIds?.let { filterTopicIds ->
                                networkNewsResource.topics.intersect(filterTopicIds).isNotEmpty()
                            },
                        )
                            .all(true::equals)
                    }
                    .map(NetworkNewsResource::asEntity)
                    .map(NewsResourceEntity::asExternalModel),
            )
        }.flowOn(ioDispatcher)

    override suspend fun syncWith(synchronizer: Synchronizer) = true
}
