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

plugins {
    id("upnews.android.feature")
    id("upnews.android.library.compose")
    id("upnews.android.library.jacoco")
}

android {
    namespace = "com.upnews.feature.search"
}

dependencies {
    implementation(project(":feature:bookmarks"))
    implementation(project(":feature:foryou"))
    implementation(project(":feature:sources"))
    implementation(libs.kotlinx.datetime)
}

