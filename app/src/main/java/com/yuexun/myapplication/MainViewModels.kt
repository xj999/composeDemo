package com.yuexun.myapplication

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.tencent.mmkv.MMKV
import com.yuexun.myapplication.app.ComposeViewModel
import com.yuexun.myapplication.data.HybridAppRepository
import com.yuexun.myapplication.data.db.entity.CommonApp
import com.yuexun.myapplication.data.db.entity.TagApp
import com.yuexun.myapplication.data.db.entity.generateTestData
import com.yuexun.myapplication.network.Api
import com.yuexun.myapplication.ui.composable.HomeEvent
import com.yuexun.myapplication.ui.composable.HomeState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class MainViewModels @Inject constructor(
    private val hybridAppRepository: HybridAppRepository
) : ComposeViewModel<HomeState, HomeEvent>() {

    private val myApps = mutableStateOf(listOf<CommonApp>())

    private val tenantName = mutableStateOf("")
    private val mk = MMKV.defaultMMKV()


    @Composable
    override fun uiState(): HomeState {
        Timber.e("=-------------------------------")
        LaunchedEffect(Unit) {
            start()
        }
        return HomeState(
            tenantName = tenantName.value,
            false,
            myApp = myApps.value,
            allApp = emptyList(),
            listOf("1", "2"),
            listOf("3", "4"),
            listOf("5", "6"),
            false
        )

    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onEvent(event: HomeEvent) {
        when (event) {
            is HomeEvent.OnNameBtnClick -> {
                val current = LocalDateTime.now()
                tenantName.value = "测试名称" + current.second
                mk.putString("tenantName", tenantName.value)
                viewModelScope.launch {
                    hybridAppRepository.saveMyAPP(generateTestData())
                }

            }

            is HomeEvent.OnPreviousMonth -> {}
        }

    }

    private fun start() {
        viewModelScope.launch {
            tenantName.value = mk.getString("tenantName", "testCompany").toString()
            hybridAppRepository.getAllCommonApps().collect { apps ->
                myApps.value = apps
            }

        }
    }


    fun loadNetData() {
        viewModelScope.launch {
            val res = Api.inquireClassifiedPluginListForTeacherAccount()
            Timber.e(res.toString())
            val modifiedList = res.commonAppList.map { commonApp ->
                val modifiedUuid =
                    "https://st.yuexunit.com/fs/api/v1.0/viewPic.file?fileUuid=${commonApp.appLogoUuid}"
                commonApp.copy(appLogoUuid = modifiedUuid)
            }
            hybridAppRepository.saveOrUpdateCommonApps(modifiedList)
            val tagList = res.tagAppList.map { ti ->
                TagApp(ti.tagId, ti.tagName)
            }
            hybridAppRepository.saveTagList(tagList)
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
                hybridAppRepository.saveHybridApp(tar)
            }
        }
    }


}