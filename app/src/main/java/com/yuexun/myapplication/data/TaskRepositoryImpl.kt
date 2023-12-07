package com.yuexun.myapplication.data

import com.yuexun.myapplication.data.db.AppDatabase
import com.yuexun.myapplication.data.db.entity.CommonApp
import com.yuexun.myapplication.network.Api
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flattenMerge
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.mapLatest

@OptIn(ExperimentalCoroutinesApi::class)
class TaskRepositoryImpl(private val appDatabase: AppDatabase) : TaskRepository {
    override fun getAllLocalCommonApps(): Flow<List<CommonApp>> {
        return appDatabase.commonAppDao().getAll()
    }


    override fun getAllCommonApps(): Flow<List<CommonApp>> {
        val databaseFlow = appDatabase.commonAppDao().getAll()

        val networkFlow = flow {
            val res = Api.inquireClassifiedPluginListForTeacherAccount()
            val modifiedList = res.commonAppList.map { commonApp ->
                val modifiedUuid = "https://st.yuexunit.com/fs/api/v1.0/viewPic.file?fileUuid=${commonApp.appLogoUuid}"
                commonApp.copy(appLogoUuid = modifiedUuid)
            }
            emit(modifiedList)
        }

        // 使用 mapLatest 操作符，将数据库结果和网络请求结果进行合并
        return flowOf(databaseFlow, networkFlow)
            .flattenMerge()
            .mapLatest { networkResult ->
                val databaseResult = databaseFlow.first()
                mergeResults(databaseResult, networkResult)
            }
    }

    private suspend fun mergeResults(
        databaseResult: List<CommonApp>,
        networkResult: List<CommonApp>
    ): List<CommonApp> {
        val mergedList = mutableListOf<CommonApp>()
        val updateList = mutableListOf<CommonApp>()
        val insertList = mutableListOf<CommonApp>()
        for (networkItem in networkResult) {
            val existingItem = databaseResult.firstOrNull { it.appKey == networkItem.appKey && it.latestVersion != networkItem.latestVersion }
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
        appDatabase.commonAppDao().update(*updateList.toTypedArray())
        appDatabase.commonAppDao().insertAll(*insertList.toTypedArray())
        appDatabase.commonAppDao().deleteAll(*missingInNetworkList.toTypedArray())
        return mergedList
    }


    override suspend fun saveOrUpdateCommonApps(list: List<CommonApp>) {
        appDatabase.commonAppDao().insertAll(*list.toTypedArray())
    }

    override suspend fun saveData(app: CommonApp) {
        appDatabase.commonAppDao().insertAll(app)
    }


}
