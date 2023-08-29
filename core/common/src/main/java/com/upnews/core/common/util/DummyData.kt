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

package com.upnews.core.common.util

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.upnews.core.common.util.DummyData.newsResources
import com.upnews.core.model.data.CategoryType
import com.upnews.core.model.data.DarkThemeConfig
import com.upnews.core.model.data.NewsResource
import com.upnews.core.model.data.Source
import com.upnews.core.model.data.ThemeBrand
import com.upnews.core.model.data.UserData

/* ktlint-disable max-line-length */
/**
 * This [PreviewParameterProvider](https://developer.android.com/reference/kotlin/androidx/compose/ui/tooling/preview/PreviewParameterProvider)
 */
class PreviewParameterProvider : PreviewParameterProvider<List<NewsResource>> {

    override val values: Sequence<List<NewsResource>> = sequenceOf(newsResources)
}

object DummyData {

    private val userData: UserData = UserData(
        themeBrand = ThemeBrand.ANDROID,
        darkThemeConfig = DarkThemeConfig.DARK,
        useDynamicColor = false,
        category = CategoryType.GENERAL,
        categorySource = null,
        country = com.upnews.core.model.data.Country.INDONESIA
    )

    val newsResources = listOf(
        NewsResource(
            sourceId = "1",
            sourceName = "Android Developers Blog",
            author = "Android Developers",
            title = "Android Basics with Compose",
            description = "We released the first two units of Android Basics with Compose, our first free course that teaches Android Development with Jetpack Compose to anyone; you do not need any prior programming experience other than basic computer literacy to get started. You’ll learn the fundamentals of programming in Kotlin while building Android apps using Jetpack Compose, Android’s modern toolkit that simplifies and accelerates native UI development. These two units are just the beginning; more will be coming soon. Check out Android Basics with Compose to get started on your Android development journey",
            url = "https://android-developers.googleblog.com/2022/05/new-android-basics-with-compose-course.html",
            urlToImage = "https://developer.android.com/images/hero-assets/android-basics-compose.svg",
            publishedAt = "2023-08-28T17:22:22.735903Z",
            content = "We released the first two units of Android Basics with Compose, our first free course that teaches Android Development with Jetpack Compose to anyone; you do not need any prior programming experience other than basic computer literacy to get started. You’ll learn the fundamentals of programming in Kotlin while building Android apps using Jetpack Compose, Android’s modern toolkit that simplifies and accelerates native UI development. These two units are just the beginning; more will be coming soon. Check out Android Basics with Compose to get started on your Android development journey",
        ),
        NewsResource(
            sourceId = "2",
            sourceName = "Android Developers Blog",
            author = "Android Developers",
            title = "Pagination With Paging 3",
            description = "Paging 3 is the recommended way to load data gradually and gracefully within your app's RecyclerView. It's built on top of Kotlin coroutines and Flow, and it's designed to integrate seamlessly with Room, LiveData, and ViewModel. It also works with RxJava and RxKotlin. Paging 3 is a complete rewrite of Paging 2, and it includes many improvements and new features. It's available as a release candidate, and we encourage you to try it out and provide feedback. In this blog post, we'll show you how to use Paging 3 to load data from a network API into a RecyclerView.",
            url = "https://android-developers.googleblog.com/2022/05/pagination-with-paging-3.html",
            urlToImage = "https://developer.android.com/images/hero-assets/paging-3.svg",
            publishedAt = "2023-08-28T17:22:22.735903Z",
            content = "Paging 3 is the recommended way to load data gradually and gracefully within your app's RecyclerView. It's built on top of Kotlin coroutines and Flow, and it's designed to integrate seamlessly with Room, LiveData, and ViewModel. It also works with RxJava and RxKotlin. Paging 3 is a complete rewrite of Paging 2, and it includes many improvements and new features. It's available as a release candidate, and we encourage you to try it out and provide feedback. In this blog post, we'll show you how to use Paging 3 to load data from a network API into a RecyclerView.",
        ),
        NewsResource(
            sourceId = "3",
            sourceName = "Android Developers Blog",
            author = "Android Developers",
            title = "Android 12 Beta 2",
            description = "Today we’re bringing you the second Beta of Android 12, Beta 2, for your testing and feedback. Beta 2 adds new privacy features like the Privacy Dashboard and continues our work of refining the release. It includes updated APIs and new behaviors across the OS.",
            url = "https://android-developers.googleblog.com/2022/05/android-12-beta-2.html",
            urlToImage = "https://developer.android.com/images/hero-assets/android-12-beta-2.svg",
            publishedAt = "2023-08-28T17:22:22.735903Z",
            content = "Today we’re bringing you the second Beta of Android 12, Beta 2, for your testing and feedback. Beta 2 adds new privacy features like the Privacy Dashboard and continues our work of refining the release. It includes updated APIs and new behaviors across the OS.",
        ),
    )

    val sources = listOf(
        Source(
            id = "1",
            name = "Android Developers Blog",
            description = "Android Developers Blog",
            url = "https://android-developers.googleblog.com/",
            category = CategoryType.GENERAL,
            language = "en",
            country = "us",
        ),
        Source(
            id = "2",
            name = "Android Police",
            description = "Looking after everything Android",
            url = "https://www.androidpolice.com/",
            category = CategoryType.GENERAL,
            language = "en",
            country = "us",
        ),
        Source(
            id = "3",
            name = "9to5Google",
            description = "Breaking news on all things Google and Android. We provide breaking Google Pixel news, everything Android, Google Home, Google apps, Chromebooks, and more!",
            url = "https://9to5google.com/",
            category = CategoryType.GENERAL,
            language = "en",
            country = "us",
        ),
    )
}
