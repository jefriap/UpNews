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

package com.upnews.app.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration.Indefinite
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTagsAsResourceId
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import com.upnews.app.R
import com.upnews.app.navigation.TopLevelDestination
import com.upnews.app.navigation.UpNewsNavHost
import com.upnews.core.data.util.NetworkMonitor
import com.upnews.core.designsystem.component.UpNewsBackground
import com.upnews.core.designsystem.component.UpNewsGradientBackground
import com.upnews.core.designsystem.component.UpNewsNavigationBar
import com.upnews.core.designsystem.component.UpNewsNavigationBarItem
import com.upnews.core.designsystem.component.UpNewsNavigationRail
import com.upnews.core.designsystem.component.UpNewsNavigationRailItem
import com.upnews.core.designsystem.component.UpNewsTopAppBar
import com.upnews.core.designsystem.icon.UpNewsIcons
import com.upnews.core.designsystem.theme.GradientColors
import com.upnews.core.designsystem.theme.LocalGradientColors
import com.upnews.core.designsystem.theme.UpNewsTheme
import com.upnews.core.ui.DevicePreviews
import com.upnews.core.ui.component.SearchToolbar
import com.upnews.feature.settings.SettingsDialog
import com.upnews.feature.settings.R as settingsR

@OptIn(
    ExperimentalMaterial3Api::class,
    ExperimentalLayoutApi::class,
    ExperimentalComposeUiApi::class,
)
@Composable
fun UpNewsApp(
    windowSizeClass: WindowSizeClass,
    networkMonitor: NetworkMonitor,
    appState: UpNewsAppState = rememberUpNewsAppState(
        networkMonitor = networkMonitor,
        windowSizeClass = windowSizeClass,
    ),
) {
    val shouldShowGradientBackground =
        appState.currentTopLevelDestination == TopLevelDestination.FOR_YOU
    var showSettingsDialog by rememberSaveable {
        mutableStateOf(false)
    }

    UpNewsBackground {
        UpNewsGradientBackground(
            gradientColors = if (shouldShowGradientBackground) {
                LocalGradientColors.current
            } else {
                GradientColors()
            },
        ) {
            val snackbarHostState = remember { SnackbarHostState() }

            val isOffline by appState.isOffline.collectAsStateWithLifecycle()

            // If user is not connected to the internet show a snack bar to inform them.
            val notConnectedMessage = stringResource(R.string.not_connected)
            LaunchedEffect(isOffline) {
                if (isOffline) {
                    snackbarHostState.showSnackbar(
                        message = notConnectedMessage,
                        duration = Indefinite,
                    )
                }
            }

            if (showSettingsDialog) {
                SettingsDialog(
                    onDismiss = { showSettingsDialog = false },
                )
            }

            var shouldSearchSources by rememberSaveable {
                mutableStateOf(false)
            }
            var querySourcesSearch = rememberSaveable {
                mutableStateOf("")
            }

            Scaffold(
                modifier = Modifier.semantics {
                    testTagsAsResourceId = true
                },
                containerColor = Color.Transparent,
                contentColor = MaterialTheme.colorScheme.onBackground,
                contentWindowInsets = WindowInsets(0, 0, 0, 0),
                snackbarHost = { SnackbarHost(snackbarHostState) },
                bottomBar = {
                    if (appState.shouldShowBottomBar) {
                        UpNewsBottomBar(
                            destinations = appState.topLevelDestinations,
                            onNavigateToDestination = appState::navigateToTopLevelDestination,
                            currentDestination = appState.currentDestination,
                            modifier = Modifier.testTag("UpNewsBottomBar"),
                        )
                    }
                },
            ) { padding ->
                Row(
                    Modifier
                        .fillMaxSize()
                        .padding(padding)
                        .consumeWindowInsets(padding)
                        .windowInsetsPadding(
                            WindowInsets.safeDrawing.only(
                                WindowInsetsSides.Horizontal,
                            ),
                        ),
                ) {
                    if (appState.shouldShowNavRail) {
                        UpNewsNavRail(
                            destinations = appState.topLevelDestinations,
                            onNavigateToDestination = appState::navigateToTopLevelDestination,
                            currentDestination = appState.currentDestination,
                            modifier = Modifier
                                .testTag("UpNewsNavRail")
                                .safeDrawingPadding(),
                        )
                    }

                    Column(Modifier.fillMaxSize()) {
                        // Show the top app bar on top level destinations.
                        val destination = appState.currentTopLevelDestination
                        if (destination != null && !shouldSearchSources) {
                            UpNewsTopAppBar(
                                titleRes = destination.titleTextId,
                                navigationIcon = UpNewsIcons.Search,
                                navigationIconContentDescription = stringResource(
                                    id = settingsR.string.top_app_bar_navigation_icon_description,
                                ),
                                actionIcon = UpNewsIcons.Settings,
                                actionIconContentDescription = stringResource(
                                    id = settingsR.string.top_app_bar_action_icon_description,
                                ),
                                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                                    containerColor = Color.Transparent,
                                ),
                                onActionClick = { showSettingsDialog = true },
                                onNavigationClick = {
                                    if (destination == TopLevelDestination.SOURCES) {
                                        shouldSearchSources = true
                                    } else {
                                        appState.navigateToSearch()
                                    }
                                },
                            )
                        } else {
                            SearchToolbar(
                                modifier = Modifier.padding(top = 10.dp),
                                onBackClick = {
                                    shouldSearchSources = false
                                },
                                onSearchQueryChanged = {
                                    querySourcesSearch.value = it
                                },
                                searchQuery = querySourcesSearch.value,
                            )
                        }

                        UpNewsNavHost(appState = appState, querySourcesSearch = querySourcesSearch)
                    }

                    // TODO: We may want to add padding or spacer when the snackbar is shown so that
                    //  content doesn't display behind it.
                }
            }
        }
    }
}

@Composable
private fun UpNewsNavRail(
    destinations: List<TopLevelDestination>,
    onNavigateToDestination: (TopLevelDestination) -> Unit,
    currentDestination: NavDestination?,
    modifier: Modifier = Modifier,
) {
    UpNewsNavigationRail(modifier = modifier) {
        destinations.forEach { destination ->
            val selected = currentDestination.isTopLevelDestinationInHierarchy(destination)
            UpNewsNavigationRailItem(
                selected = selected,
                onClick = { onNavigateToDestination(destination) },
                icon = {
                    Icon(
                        imageVector = destination.unselectedIcon,
                        contentDescription = null,
                    )
                },
                selectedIcon = {
                    Icon(
                        imageVector = destination.selectedIcon,
                        contentDescription = null,
                    )
                },
                label = { Text(stringResource(destination.iconTextId)) },
            )
        }
    }
}

@Composable
private fun UpNewsBottomBar(
    destinations: List<TopLevelDestination>,
    onNavigateToDestination: (TopLevelDestination) -> Unit,
    currentDestination: NavDestination?,
    modifier: Modifier = Modifier,
) {
    UpNewsNavigationBar(
        modifier = modifier,
    ) {
        destinations.forEach { destination ->
            val selected = currentDestination.isTopLevelDestinationInHierarchy(destination)
            UpNewsNavigationBarItem(
                selected = selected,
                onClick = { onNavigateToDestination(destination) },
                icon = {
                    Icon(
                        imageVector = destination.unselectedIcon,
                        contentDescription = null,
                    )
                },
                selectedIcon = {
                    Icon(
                        imageVector = destination.selectedIcon,
                        contentDescription = null,
                    )
                },
                label = { Text(stringResource(destination.iconTextId)) },
            )
        }
    }
}

private fun NavDestination?.isTopLevelDestinationInHierarchy(destination: TopLevelDestination) =
    this?.hierarchy?.any {
        it.route?.contains(destination.name, true) ?: false
    } ?: false


@DevicePreviews
@Composable
private fun UpNewsNavRailPreviews() {
    UpNewsTheme {
        UpNewsNavRail(
            destinations = TopLevelDestination.values().asList(),
            onNavigateToDestination = {},
            currentDestination = null,
        )
    }
}

@DevicePreviews
@Composable
private fun UpNewsBottomBarPreviews() {
    UpNewsTheme {
        UpNewsBottomBar(
            destinations = TopLevelDestination.values().asList(),
            onNavigateToDestination = {},
            currentDestination = null,
        )
    }
}