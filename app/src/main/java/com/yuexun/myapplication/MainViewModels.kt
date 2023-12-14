package com.yuexun.myapplication

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.midai.data.db.entity.HybridApp
import com.midai.data.db.entity.TagWithHybridAppList
import com.tencent.mmkv.MMKV
import com.yuexun.myapplication.app.ComposeViewModel
import com.yuexun.myapplication.app.Constants.APP_SWITCH
import com.yuexun.myapplication.app.Constants.TENANT_NAME
import com.yuexun.myapplication.ui.homeScreen.HomeEvent
import com.yuexun.myapplication.ui.homeScreen.HomeState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class MainViewModels @Inject constructor(
    private val hybridAppRepository: com.midai.data.HybridAppRepository
) : ComposeViewModel<HomeState, HomeEvent>() {

    private val myApps = mutableStateOf(listOf<HybridApp>())

    private val hybridApps = mutableStateOf(listOf<TagWithHybridAppList>())

    private val tenantName = mutableStateOf("")

    private val expanded = mutableStateOf(false)

    private val mk = MMKV.defaultMMKV()

    init{
        viewModelScope.launch {
            Timber.e("-----------------------------------------")
            hybridAppRepository.fetchRemoteAppData()
        }
    }
    @Composable
    override fun uiState(): HomeState {
        LaunchedEffect(Unit) {
            start()
        }
        return HomeState(
            tenantName = tenantName.value,
            false,
            myApp = myApps.value,
            allApp = hybridApps.value.sortedByDescending { it.tag.tagId },
            listOf("1", "2"),
            listOf("3", "4"),
            listOf("5", "6"),
            expanded = expanded.value
        )

    }

    override fun onEvent(event: HomeEvent) {
        when (event) {
            is HomeEvent.OnNameBtnClick -> {
                val current = LocalDateTime.now()
                tenantName.value = "测试名称" + current.second
                mk.putString(TENANT_NAME, tenantName.value)
                viewModelScope.launch {
//                    hybridAppRepository.saveMyAPP(com.midai.data.db.entity.generateTestData())
                }
            }

            is HomeEvent.OnAppSwitchClick -> {
                Timber.e("switch click -----------------------")
                expanded.value = !expanded.value
                mk.putBoolean(APP_SWITCH, expanded.value)
            }

            is HomeEvent.OnAppItemClick -> {
                if (event.app is HybridApp) {
                    Timber.e("app click %s", event.app.appName)
                }

            }

            is HomeEvent.OnScanClick -> {
                Timber.e("OnScanClick   %s", event.toString())
            }
        }

    }

    private fun start() {

        viewModelScope.launch(errorHandlerMessage) {
            Timber.e("===========================================")
            tenantName.value = mk.getString(TENANT_NAME, "testCompany").toString()
            expanded.value = mk.getBoolean(APP_SWITCH, false)
            val commonAppsFlow = hybridAppRepository.getLocalMyApps()
            val hybridAppsFlow = hybridAppRepository.getAllTagWithHybridApps()
            Timber.e("  22222222222222222222222")
            combine(commonAppsFlow, hybridAppsFlow) { commonApps, hybrid ->
                myApps.value = commonApps
                hybridApps.value = hybrid
                Timber.e("  3333333333333333333333333")
            }.collect()
        }

    }


}