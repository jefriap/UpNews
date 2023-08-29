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

package com.upnews.feature.foryou.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink
import com.upnews.feature.foryou.ForYouRoute

const val LINKED_NEWS_RESOURCE_ID = "linkedNewsResourceId"
const val forYouNavigationRoute = "for_you_route/{$LINKED_NEWS_RESOURCE_ID}"

fun NavController.navigateToForYou(navOptions: NavOptions? = null) {
    this.navigate(forYouNavigationRoute, navOptions)
}

fun NavGraphBuilder.forYouScreen(onSourceClick: (id: String, name: String) -> Unit) {
    composable(
        route = forYouNavigationRoute,
        arguments = listOf(
            navArgument(LINKED_NEWS_RESOURCE_ID) { type = NavType.StringType },
        ),
    ) {
        ForYouRoute(onSourceClick)
    }
}
