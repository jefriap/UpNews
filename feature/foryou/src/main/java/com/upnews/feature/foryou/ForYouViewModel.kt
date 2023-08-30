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

package com.upnews.feature.foryou

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.upnews.core.data.repository.NewsRepo
import com.upnews.core.data.repository.NewsResourceQuery
import com.upnews.core.data.repository.UserDataRepo
import com.upnews.core.model.data.CategoryType
import com.upnews.core.model.data.Country
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ForYouViewModel @Inject constructor(
    private val newsRepo: NewsRepo,
    private val userDataRepo: UserDataRepo,
) : ViewModel() {

    val selectedCategory = userDataRepo.userData.map {
        it.category
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = CategoryType.GENERAL,
        )

    val newsResources = combine(
        selectedCategory,
        userDataRepo.userData.map { it.country }
    ) { category, country ->
        NewsResourceQuery(
            category = category,
            country = country
        )
    }.flatMapLatest {
        newsRepo.getNewsResources(
            query = it,
        )
    }.cachedIn(viewModelScope)

    fun setEvent(event: ForYouEvent) {
        when (event) {
            is ForYouEvent.PerformChangeCategory -> {
                viewModelScope.launch {
                    userDataRepo.setCategoryPreference(event.category)
                }
            }
        }
    }
}
