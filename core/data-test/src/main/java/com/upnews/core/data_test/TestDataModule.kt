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

package com.upnews.core.data_test

import com.upnews.core.data.di.DataModule
import com.upnews.core.data.repository.NewsRepo
import com.upnews.core.data.repository.RecentSearchRepository
import com.upnews.core.data.repository.SearchContentsRepository
import com.upnews.core.data.repository.TopicsRepository
import com.upnews.core.data.repository.UserDataRepository
import com.upnews.core.data.repository.fake.FakeNewsRepo
import com.upnews.core.data.repository.fake.FakeRecentSearchRepository
import com.upnews.core.data.repository.fake.FakeSearchContentsRepository
import com.upnews.core.data.repository.fake.FakeTopicsRepository
import com.upnews.core.data.repository.fake.FakeUserDataRepository
import com.upnews.core.data.util.NetworkMonitor
import dagger.Binds
import dagger.Module
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [DataModule::class],
)
interface TestDataModule {
    @Binds
    fun bindsTopicRepository(
        fakeTopicsRepository: FakeTopicsRepository,
    ): TopicsRepository

    @Binds
    fun bindsNewsResourceRepository(
        fakeNewsRepository: FakeNewsRepo,
    ): NewsRepo

    @Binds
    fun bindsUserDataRepository(
        userDataRepository: FakeUserDataRepository,
    ): UserDataRepository

    @Binds
    fun bindsRecentSearchRepository(
        recentSearchRepository: FakeRecentSearchRepository,
    ): RecentSearchRepository

    @Binds
    fun bindsSearchContentsRepository(
        searchContentsRepository: FakeSearchContentsRepository,
    ): SearchContentsRepository

    @Binds
    fun bindsNetworkMonitor(
        networkMonitor: AlwaysOnlineNetworkMonitor,
    ): NetworkMonitor
}
