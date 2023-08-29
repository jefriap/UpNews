/*
 * Copyright 2023 The Android Open Source Project
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

package com.upnews.feature.search

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.upnews.feature.foryou.ForYouViewModel
import com.upnews.feature.sources.SourcesViewModel

@Composable
internal fun SearchRoute(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit,
    onSourcesClick: () -> Unit,
    onSourceClick: (id: String, name: String) -> Unit,
    sourcesViewModel: SourcesViewModel = hiltViewModel(),
    searchViewModel: SearchViewModel = hiltViewModel(),
    forYouViewModel: ForYouViewModel = hiltViewModel(),
) {

}

@Composable
internal fun SearchScreen(
) {}