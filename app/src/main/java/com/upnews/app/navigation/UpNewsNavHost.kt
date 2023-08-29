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

package com.upnews.app.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import com.upnews.app.navigation.TopLevelDestination.SOURCES
import com.upnews.app.ui.UpNewsAppState
import com.upnews.feature.foryou.navigation.forYouNavigationRoute
import com.upnews.feature.foryou.navigation.forYouScreen
import com.upnews.feature.sources.navigation.sourcesGraph
import com.upnews.feature.search.navigation.searchScreen
import com.upnews.feature.source.navigation.navigateToSource
import com.upnews.feature.source.navigation.sourceScreen

/**
 * Top-level navigation graph. Navigation is organized as explained at
 * https://d.android.com/jetpack/compose/nav-adaptive
 *
 * The navigation graph defined in this file defines the different top level routes. Navigation
 * within each route is handled using state and Back Handlers.
 */
@Composable
fun UpNewsNavHost(
    appState: UpNewsAppState,
    modifier: Modifier = Modifier,
    startDestination: String = forYouNavigationRoute,
) {
    val navController = appState.navController
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier,
    ) {
        forYouScreen(onSourceClick = navController::navigateToSource)
        searchScreen(
            onBackClick = navController::popBackStack,
            onSourcesClick = { appState.navigateToTopLevelDestination(SOURCES) },
            onSourceClick = navController::navigateToSource,
        )
        sourcesGraph(
            onSourceClick = navController::navigateToSource,
            nestedGraphs = {
                sourceScreen(
                    onBackClick = navController::popBackStack,
                    onSourceClick = navController::navigateToSource,
                )
            },
        )
    }
}
