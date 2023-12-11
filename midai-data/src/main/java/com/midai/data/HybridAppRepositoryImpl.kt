package com.midai.data

import com.midai.data.db.AppDatabase
import com.midai.data.db.entity.ApiResponseEntity
import com.midai.data.db.entity.AppType
import com.midai.data.db.entity.HybridApp
import com.midai.data.db.entity.TagApp
import com.midai.data.db.entity.TagWithHybridAppList
import com.midai.data.network.Api
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext

@OptIn(ExperimentalCoroutinesApi::class)
class HybridAppRepositoryImpl(private val appDatabase: AppDatabase) : HybridAppRepository {
    override suspend fun fetchRemoteAppData(): ApiResponseEntity = withContext(Dispatchers.IO) {
        val res = Api.inquireClassifiedPluginListForTeacherAccount()
     val myapp=   res.commonAppList.map { commonApp ->
            val modifiedUuid =
                "https://st.yuexunit.com/fs/api/v1.0/viewPic.file?fileUuid=${commonApp.appLogoUuid}"
            commonApp.copy(appLogoUuid = modifiedUuid,type = AppType.MYAPP)

        }
        saveHybridApp(myapp)
        val tagList = res.tagAppList.map { ti ->
            TagApp(ti.tagId, ti.tagName)
        }
        saveTagList(tagList)
        for (ti in res.tagAppList) {
            val tar = ti.hybridAppList.map { hybridAppList ->
                if (hybridAppList.appLogoUuid.isNotBlank()) {
                    val modifiedUuid =
                        "https://st.yuexunit.com/fs/api/v1.0/viewPic.file?fileUuid=${hybridAppList.appLogoUuid}"
                    hybridAppList.copy(appLogoUuid = modifiedUuid,type = AppType.HybridApp)
                } else {
                    hybridAppList.copy(appLogoUuid = "https://cdn.pixabay.com/photo/2023/11/21/21/38/puffins-8404284_1280.jpg",type = AppType.HybridApp)
                }

            }
            saveHybridApp(tar)
        }
        return@withContext res
    }

    override fun getLocalMyApps(): Flow<List<HybridApp>> =
        appDatabase.commonAppDao().getAll().flowOn(Dispatchers.IO)

    override fun getLocalHybridApps(): Flow<List<HybridApp>> =
        appDatabase.hybridAppDao().getAll()
            .flowOn(Dispatchers.IO)



    override suspend fun saveOrUpdateCommonApps(list: List<HybridApp>) = withContext(Dispatchers.IO) {
        appDatabase.commonAppDao().upsert(*list.toTypedArray())
    }

    override suspend fun saveMyAPP(app: HybridApp) = withContext(Dispatchers.IO) {
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
