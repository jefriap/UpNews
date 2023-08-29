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

package com.upnews.core.data.repository

import com.upnews.core.model.data.CategoryType
import com.upnews.core.model.data.Country
import com.upnews.core.model.data.DarkThemeConfig
import com.upnews.core.model.data.ThemeBrand
import com.upnews.core.model.data.UserData
import kotlinx.coroutines.flow.Flow

interface UserDataRepo {

    /**
     * Stream of [UserData]
     */
    val userData: Flow<UserData>
    /**
     * Sets the desired theme brand.
     */
    suspend fun setThemeBrand(themeBrand: ThemeBrand)

    /**
     * Sets the desired dark theme config.
     */
    suspend fun setDarkThemeConfig(darkThemeConfig: DarkThemeConfig)

    /**
     * Sets the preferred dynamic color config.
     */
    suspend fun setDynamicColorPreference(useDynamicColor: Boolean)

    /**
     * Sets the preferred category config.
     */
    suspend fun setCategoryPreference(category: CategoryType)

    /**
     * Sets the preferred category source config.
     */
    suspend fun setCategorySourcesPreference(category: CategoryType?)

    /**
     * Sets the preferred country config.
     */
    suspend fun setCountry(country: Country)

}
