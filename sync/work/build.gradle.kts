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
plugins {
    id("upnews.android.library")
    id("upnews.android.library.jacoco")
    id("upnews.android.hilt")
}

android {
    defaultConfig {
        testInstrumentationRunner = "com.upnews.core.testing.UpNewsTestRunner"
    }
    namespace = "com.upnews.app.sync"
}

dependencies {
    implementation(project(":core:analytics"))
    implementation(project(":core:common"))
    implementation(project(":core:data"))
    implementation(project(":core:datastore"))
    implementation(project(":core:model"))
    implementation(libs.androidx.lifecycle.livedata.ktx)
    implementation(libs.androidx.tracing.ktx)
    implementation(libs.androidx.work.ktx)
    implementation(libs.firebase.cloud.messaging)
    implementation(libs.hilt.ext.work)
    implementation(libs.kotlinx.coroutines.android)

    kapt(libs.hilt.ext.compiler)

    testImplementation(project(":core:testing"))

    androidTestImplementation(project(":core:testing"))
    androidTestImplementation(libs.androidx.work.testing)
}
