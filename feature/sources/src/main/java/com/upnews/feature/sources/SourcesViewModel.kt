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

package com.upnews.feature.sources

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.upnews.core.common.result.Result
import com.upnews.core.data.repository.SourcesQuery
import com.upnews.core.data.repository.SourcesRepo
import com.upnews.core.data.repository.UserDataRepo
import com.upnews.core.model.data.CategoryType
import com.upnews.core.model.data.Source
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SourcesViewModel @Inject constructor(
    private val sourcesRepo: SourcesRepo,
    private val userDataRepo: UserDataRepo,
) : ViewModel() {

    val selectedCategory = userDataRepo.userData.map {
        it.categorySource
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = CategoryType.GENERAL,
    )

    val sources = combine(
        selectedCategory,
        userDataRepo.userData.map { it.country }
    ) { category, country ->
        SourcesQuery(
            category = category,
            country = country
        )
    }.flatMapLatest {
        sourcesRepo.getSources(
            query = it,
        ).map { result ->
            when (result) {
                is Result.Error -> {
                    SourcesUiState.Error(result.message ?: "Error")
                }

                is Result.Loading -> {
                    SourcesUiState.Loading
                }

                is Result.Success -> {
                    SourcesUiState.Success(result.data!!)
                }
            }
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = SourcesUiState.Loading
    )

    fun setEvent(event: SourcesEvent) {
        when (event) {
            is SourcesEvent.PerformChangeCategory -> {
                viewModelScope.launch {
                    userDataRepo.setCategorySourcesPreference(event.category)
                }
            }
        }
    }
}

sealed interface SourcesUiState {
    data object Loading : SourcesUiState
    data class Success(val sources: List<Source>) : SourcesUiState
    data class Error(val message: String) : SourcesUiState
}