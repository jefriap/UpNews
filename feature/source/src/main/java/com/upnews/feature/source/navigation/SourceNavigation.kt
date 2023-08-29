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

package com.upnews.feature.source.navigation

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.upnews.feature.source.SourceRoute
import java.net.URLDecoder
import java.net.URLEncoder
import kotlin.text.Charsets.UTF_8

private val URL_CHARACTER_ENCODING = UTF_8.name()

@VisibleForTesting
internal const val sourceIdArg = "sourceId"
internal const val sourceNameArg = "sourceName"

internal class SourceArgs(val sourceId: String, val sourceName: String) {
    constructor(savedStateHandle: SavedStateHandle) :
        this(
            URLDecoder.decode(checkNotNull(savedStateHandle[sourceIdArg]), URL_CHARACTER_ENCODING),
            URLDecoder.decode(checkNotNull(savedStateHandle[sourceNameArg]), URL_CHARACTER_ENCODING),
        )
}

fun NavController.navigateToSource(sourceId: String, sourceName: String) {
    val encodedId = URLEncoder.encode(sourceId, URL_CHARACTER_ENCODING)
    val encodedName = URLEncoder.encode(sourceName, URL_CHARACTER_ENCODING)
    this.navigate("source_route/$encodedId/$encodedName") {
        launchSingleTop = true
    }
}

fun NavGraphBuilder.sourceScreen(
    onBackClick: () -> Unit,
    onSourceClick: (id: String, name: String) -> Unit,
) {
    composable(
        route = "source_route/{$sourceIdArg}/{$sourceNameArg}",
        arguments = listOf(
            navArgument(sourceIdArg) { type = NavType.StringType },
            navArgument(sourceNameArg) { type = NavType.StringType },
        ),
    ) {
        SourceRoute(onBackClick = onBackClick, onSourceClick = onSourceClick)
    }
}
