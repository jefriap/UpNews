/*
 * Copyright 2021 The Android Open Source Project
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

package com.upnews.feature.source

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.upnews.core.data.repository.NewsRepo
import com.upnews.core.data.repository.NewsResourceQuery
import com.upnews.feature.source.navigation.SourceArgs
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class SourceViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val newsRepo: NewsRepo,
) : ViewModel() {

    val sourceArgs: SourceArgs = SourceArgs(savedStateHandle)

    val newsResources = newsRepo.getNewsResources(
        query = NewsResourceQuery(
            sourceId = sourceArgs.sourceId,
        ),
    ).cachedIn(viewModelScope)
}