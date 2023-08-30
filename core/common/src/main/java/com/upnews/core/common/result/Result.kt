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

package com.upnews.core.common.result

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart


typealias SimpleResult = Result<Unit>

sealed class Result<T>(
    val data: T? = null,
    val message: String? = null,
    val errorCode: Int? = null,
) {

    class Loading<T> : Result<T>()
    class Success<T>(data: T? = null, message: String? = null) : Result<T>(data, message)
    class Error<T>(message: String, data: T? = null, errorCode: Int? = null) : Result<T>(message = message, data = data, errorCode = errorCode)
}