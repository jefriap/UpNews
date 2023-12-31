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

package com.upnews.feature.sources.navigation

import androidx.compose.runtime.MutableState
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.upnews.feature.sources.SourcesRoute
import kotlinx.coroutines.flow.MutableStateFlow

private const val SOURCES_GRAPH_ROUTE_PATTERN = "sources_graph"
const val sourcesRoute = "sources_route"

fun NavController.navigateToSourcesGraph(navOptions: NavOptions? = null) {
    this.navigate(SOURCES_GRAPH_ROUTE_PATTERN, navOptions)
}

fun NavGraphBuilder.sourcesGraph(
    querySourcesSearch: MutableState<String>,
    onSourceClick: (id: String, name: String) -> Unit,
    nestedGraphs: NavGraphBuilder.() -> Unit,
) {
    navigation(
        route = SOURCES_GRAPH_ROUTE_PATTERN,
        startDestination = sourcesRoute,
    ) {
        composable(route = sourcesRoute) {
            SourcesRoute(
                querySourcesSearch = querySourcesSearch,
                onSourceClick
            )
        }
        nestedGraphs()
    }
}
