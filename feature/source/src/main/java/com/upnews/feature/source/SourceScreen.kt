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

import androidx.annotation.VisibleForTesting
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.upnews.core.designsystem.component.UpNewsTopAppBar
import com.upnews.core.designsystem.icon.UpNewsIcons

@Composable
internal fun SourceRoute(
    onBackClick: () -> Unit,
    onSourceClick: (id: String, name: String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: SourceViewModel = hiltViewModel(),
) {}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun SourceScreen(
    onBackClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .statusBarsPadding()
    ) {
        UpNewsTopAppBar(
            title = "",
            actionIcon =  UpNewsIcons.ArrowBack,
            actionIconContentDescription = stringResource(
                id = com.upnews.core.ui.R.string.back,
            ),
            onActionClick = onBackClick,
        )
    }
}
