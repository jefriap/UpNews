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

import com.upnews.core.datastore.UpNewsPreferencesDataSource
import com.upnews.core.model.data.CategoryType
import com.upnews.core.model.data.Country
import com.upnews.core.model.data.DarkThemeConfig
import com.upnews.core.model.data.ThemeBrand
import com.upnews.core.model.data.UserData
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class OfflineFirstUserDataRepository @Inject constructor(
    private val upNewsPreferencesDataSource: UpNewsPreferencesDataSource,
) : UserDataRepo {

    override val userData: Flow<UserData> =
        upNewsPreferencesDataSource.userData

    override suspend fun setThemeBrand(themeBrand: ThemeBrand) {
        upNewsPreferencesDataSource.setThemeBrand(themeBrand)
    }

    override suspend fun setDarkThemeConfig(darkThemeConfig: DarkThemeConfig) {
        upNewsPreferencesDataSource.setDarkThemeConfig(darkThemeConfig)
    }

    override suspend fun setDynamicColorPreference(useDynamicColor: Boolean) {
        upNewsPreferencesDataSource.setDynamicColorPreference(useDynamicColor)
    }

    override suspend fun setCategoryPreference(category: CategoryType) {
        upNewsPreferencesDataSource.setCategoryPreference(category)
    }

    override suspend fun setCategorySourcesPreference(category: CategoryType?) {
        upNewsPreferencesDataSource.setCategorySourcesPreference(category)
    }

    override suspend fun setCountry(country: Country) {
        upNewsPreferencesDataSource.setCountry(country)
    }

}
