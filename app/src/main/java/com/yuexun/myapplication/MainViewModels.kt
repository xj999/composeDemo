package com.yuexun.myapplication

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.tencent.mmkv.MMKV
import com.yuexun.myapplication.app.ComposeViewModel
import com.yuexun.myapplication.app.Constants.APP_SWITCH
import com.yuexun.myapplication.app.Constants.TENANT_NAME
import com.yuexun.myapplication.ui.composable.HomeEvent
import com.yuexun.myapplication.ui.composable.HomeState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import timber.log.Timber
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class MainViewModels @Inject constructor(
    private val hybridAppRepository: com.midai.data.HybridAppRepository
) : ComposeViewModel<HomeState, HomeEvent>() {

    private val myApps = mutableStateOf(listOf<com.midai.data.db.entity.CommonApp>())

    private val hybridApps = mutableStateOf(listOf<com.midai.data.db.entity.TagWithHybridAppList>())

    private val tenantName = mutableStateOf("")

    private val expanded = mutableStateOf(false)

    private val mk = MMKV.defaultMMKV()


    @Composable
    override fun uiState(): HomeState {
        LaunchedEffect(Unit) {
            start()
        }
        return HomeState(
            tenantName = tenantName.value,
            false,
            myApp = myApps.value,
            allApp = hybridApps.value,
            listOf("1", "2"),
            listOf("3", "4"),
            listOf("5", "6"),
            expanded = expanded.value
        )

    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onEvent(event: HomeEvent) {
        when (event) {
            is HomeEvent.OnNameBtnClick -> {
                val current = LocalDateTime.now()
                tenantName.value = "测试名称" + current.second
                mk.putString(TENANT_NAME, tenantName.value)
                viewModelScope.launch {
                    hybridAppRepository.saveMyAPP(com.midai.data.db.entity.generateTestData())
                }
            }
            is HomeEvent.OnAppSwitchClick -> {
                expanded.value = !expanded.value
                mk.putBoolean(APP_SWITCH, expanded.value)
            }
            is HomeEvent.OnAppItemClick -> {
                Timber.e("app click %s",event.app.toString())
            }
        }

    }

    private fun start() {
        viewModelScope.launch() {
           hybridAppRepository.fetchRemoteAppData()

            tenantName.value = mk.getString(TENANT_NAME, "testCompany").toString()
            expanded.value = mk.getBoolean(APP_SWITCH, false)

            val commonAppsFlow = hybridAppRepository.getLocalMyApps()
            val hybridAppsFlow = hybridAppRepository.getAllTagWithHybridApps()

            combine(commonAppsFlow, hybridAppsFlow) { commonApps, hybrid ->
                myApps.value = commonApps
                hybridApps.value = hybrid
            }.collect()


        }
    }




}