/*
 * Copyright 2019 The Android Open Source Project
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

package com.midai.data

import com.midai.data.db.entity.ApiResponseEntity
import com.midai.data.db.entity.CommonApp
import com.midai.data.db.entity.HybridApp
import com.midai.data.db.entity.TagApp
import com.midai.data.db.entity.TagWithHybridAppList
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

interface HybridAppRepository {

    suspend fun fetchRemoteAppData(): ApiResponseEntity

    fun getLocalMyApps(): Flow<List<CommonApp>>

    fun getLocalHybridApps(): Flow<List<HybridApp>>


    suspend fun saveOrUpdateCommonApps(list: List<CommonApp>)

    suspend fun saveMyAPP(app: CommonApp)


    suspend fun saveTagList(list: List<TagApp>)

    suspend fun saveHybridApp(list: List<HybridApp>)

    fun getAllTagWithHybridApps(): Flow<List<TagWithHybridAppList>>

}
