package com.midai.data

import com.midai.data.db.AppDatabase
import com.midai.data.db.entity.ApiResponseEntity
import com.midai.data.db.entity.CommonApp
import com.midai.data.db.entity.HybridApp
import com.midai.data.db.entity.TagApp
import com.midai.data.db.entity.TagWithHybridAppList
import com.midai.data.network.Api
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flattenMerge
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.withContext

@OptIn(ExperimentalCoroutinesApi::class)
class HybridAppRepositoryImpl(private val appDatabase: AppDatabase) : HybridAppRepository {
    override suspend fun fetchRemoteAppData(): ApiResponseEntity = withContext(Dispatchers.IO) {
        val res = Api.inquireClassifiedPluginListForTeacherAccount()
        res.commonAppList.map { commonApp ->
            val modifiedUuid =
                "https://st.yuexunit.com/fs/api/v1.0/viewPic.file?fileUuid=${commonApp.appLogoUuid}"
            commonApp.copy(appLogoUuid = modifiedUuid)
        }

        val tagList = res.tagAppList.map { ti ->
            TagApp(ti.tagId, ti.tagName)
        }
        saveTagList(tagList)
        for (ti in res.tagAppList) {
            val tar = ti.hybridAppList.map { hybridAppList ->
                if (!hybridAppList.appLogoUuid.isNullOrBlank()) {
                    val modifiedUuid =
                        "https://st.yuexunit.com/fs/api/v1.0/viewPic.file?fileUuid=${hybridAppList.appLogoUuid}"
                    hybridAppList.copy(appLogoUuid = modifiedUuid)
                } else {
                    hybridAppList.copy(appLogoUuid = "https://cdn.pixabay.com/photo/2023/11/21/21/38/puffins-8404284_1280.jpg")
                }

            }
            saveHybridApp(tar)
        }
        return@withContext res
    }


    override fun getLocalMyApps(): Flow<List<CommonApp>> = flow {
        val databaseFlow = appDatabase.commonAppDao().getAll()

        val networkResult = withContext(Dispatchers.IO) {
            val res = Api.inquireClassifiedPluginListForTeacherAccount()
            res.commonAppList.map { commonApp ->
                val modifiedUuid =
                    "https://st.yuexunit.com/fs/api/v1.0/viewPic.file?fileUuid=${commonApp.appLogoUuid}"
                commonApp.copy(appLogoUuid = modifiedUuid)
            }
        }

        val databaseResult = databaseFlow.first()
        emit(mergeResults(databaseResult, networkResult))
    }.flowOn(Dispatchers.IO)

    override fun getLocalHybridApps(): Flow<List<HybridApp>> =
        appDatabase.hybridAppDao().getAll()
            .flowOn(Dispatchers.IO)

    private suspend fun mergeResults(
        databaseResult: List<CommonApp>,
        networkResult: List<CommonApp>
    ): List<CommonApp> {
        val mergedList = mutableListOf<CommonApp>()
        val updateList = mutableListOf<CommonApp>()
        val insertList = mutableListOf<CommonApp>()
        for (networkItem in networkResult) {
            val existingItem =
                databaseResult.firstOrNull { it.appKey == networkItem.appKey && it.latestVersion != networkItem.latestVersion }
            if (existingItem != null) {
                val updatedItem = existingItem.copy(
                    appLogoUuid = networkItem.appLogoUuid,
                    latestVersion = networkItem.latestVersion,
                    downloadUrl = networkItem.downloadUrl,
                    messageCount = networkItem.messageCount
                )
                mergedList.add(updatedItem)
                updateList.add(updatedItem)

            } else {
                mergedList.add(networkItem)
                insertList.add(networkItem)
            }
        }
        val missingInNetworkList = mutableListOf<CommonApp>()
        for (databaseItem in databaseResult) {
            val missingInNetwork = networkResult.none { it.appKey == databaseItem.appKey }
            if (missingInNetwork) {
                missingInNetworkList.add(databaseItem)
            }
        }
        updateList.takeIf { it.size > 0 }
            ?.let { appDatabase.commonAppDao().update(*it.toTypedArray()) }
        insertList.takeIf { it.size > 0 }
            ?.let { appDatabase.commonAppDao().upsert(*it.toTypedArray()) }
        missingInNetworkList.takeIf { it.size > 0 }
            ?.let { appDatabase.commonAppDao().deleteAll(*it.toTypedArray()) }
        return mergedList
    }


    override suspend fun saveOrUpdateCommonApps(list: List<CommonApp>) = withContext(Dispatchers.IO) {
            appDatabase.commonAppDao().upsert(*list.toTypedArray())
        }

    override suspend fun saveMyAPP(app: CommonApp) = withContext(Dispatchers.IO) {
        appDatabase.commonAppDao().upsert(app)
    }

    override suspend fun saveTagList(list: List<TagApp>) = withContext(Dispatchers.IO) {
        appDatabase.tagAppDao().upsert(*list.toTypedArray())
    }

    override suspend fun saveHybridApp(list: List<HybridApp>) = withContext(Dispatchers.IO) {

        appDatabase.hybridAppDao().upsert(*list.toTypedArray())
    }

    override fun getAllTagWithHybridApps(): Flow<List<TagWithHybridAppList>> =
        appDatabase.tagAppDao().getTagWithHybridAppLists().flowOn(Dispatchers.IO)


}
