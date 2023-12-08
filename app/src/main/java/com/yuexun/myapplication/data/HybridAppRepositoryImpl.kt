package com.yuexun.myapplication.data

import com.yuexun.myapplication.data.db.AppDatabase
import com.yuexun.myapplication.data.db.entity.ApiResponseEntity
import com.yuexun.myapplication.data.db.entity.CommonApp
import com.yuexun.myapplication.data.db.entity.HybridApp
import com.yuexun.myapplication.data.db.entity.TagApp
import com.yuexun.myapplication.network.Api
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flattenMerge
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.mapLatest
import timber.log.Timber

@OptIn(ExperimentalCoroutinesApi::class)
class HybridAppRepositoryImpl(private val appDatabase: AppDatabase) : HybridAppRepository {
    override suspend fun fetchAppData(): ApiResponseEntity {
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
        return res
    }


    override fun getAllCommonApps(): Flow<List<CommonApp>> {
        val databaseFlow = appDatabase.commonAppDao().getAll()

        val networkFlow = flow {
            val res = Api.inquireClassifiedPluginListForTeacherAccount()
            val modifiedList = res.commonAppList.map { commonApp ->
                val modifiedUuid =
                    "https://st.yuexunit.com/fs/api/v1.0/viewPic.file?fileUuid=${commonApp.appLogoUuid}"
                commonApp.copy(appLogoUuid = modifiedUuid)
            }
            emit(modifiedList)
        }

        return flowOf(databaseFlow, networkFlow)
            .flattenMerge()
            .mapLatest { networkResult ->
                val databaseResult = databaseFlow.first()
                mergeResults(databaseResult, networkResult)
            }
    }

    override fun getAllHybridApps(): Flow<List<HybridApp>> {
        Timber.e("====getAllHybridApps",)
        return appDatabase.hybridAppDao().getAll()
    }

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
            ?.let { appDatabase.commonAppDao().insertAll(*it.toTypedArray()) }
        missingInNetworkList.takeIf { it.size > 0 }
            ?.let { appDatabase.commonAppDao().deleteAll(*it.toTypedArray()) }
        return mergedList
    }


    override suspend fun saveOrUpdateCommonApps(list: List<CommonApp>) {
        appDatabase.commonAppDao().insertAll(*list.toTypedArray())
    }

    override suspend fun saveMyAPP(app: CommonApp) {
        appDatabase.commonAppDao().insertAll(app)
    }

    override suspend fun saveTagList(list: List<TagApp>) {
    }

    override suspend fun saveHybridApp(list: List<HybridApp>) {
        Timber.e("++++++++++++++++%s",list.size)
        appDatabase.hybridAppDao().insertAll(*list.toTypedArray())
    }


}
