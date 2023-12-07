package com.yuexun.myapplication

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yuexun.myapplication.data.TaskRepository
import com.yuexun.myapplication.data.db.AppDatabase
import com.yuexun.myapplication.data.db.entity.CommonApp
import com.yuexun.myapplication.data.db.entity.HybridApp
import com.yuexun.myapplication.data.db.entity.TagApp
import com.yuexun.myapplication.network.Api
import com.yuexun.myapplication.ui.composable.HomeState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@HiltViewModel
class MainViewModels @Inject constructor(
    private val taskRepository: TaskRepository,
    private val db: AppDatabase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _apps = MutableStateFlow<List<CommonApp>>(emptyList())
    var uiState by mutableStateOf(HomeState())
        private set

    private val _hybridApp = MutableStateFlow<List<HybridApp>>(emptyList())
    val hybridApp: StateFlow<List<HybridApp>> get() = _hybridApp
    val app: StateFlow<List<CommonApp>> get() = _apps


    private fun loadLocalData() {
        viewModelScope.launch {
            uiState = HomeState(tenantName = "燕煌小学", false, myApp = _apps, allApp = emptyList(), listOf("1", "2"), listOf("3", "4"), listOf("5", "6"), false)

            taskRepository.getAllCommonApps().collect { apps ->
                _apps.value = apps
                Timber.d( "Apps loaded: $apps")
            }
            Timber.d( "Apps loaded end")
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun addData() {
        val current = LocalDateTime.now()
        uiState.tenantName="燕煌小学"+current.second
        Timber.d( "Apps tenant click %s",uiState.tenantName)
    }

    fun loadNetData() {
        viewModelScope.launch {
            val res = Api.inquireClassifiedPluginListForTeacherAccount()
            Timber.e(res.toString())
            val modifiedList = res.commonAppList.map { commonApp ->
                val modifiedUuid = "https://st.yuexunit.com/fs/api/v1.0/viewPic.file?fileUuid=${commonApp.appLogoUuid}"
                commonApp.copy(appLogoUuid = modifiedUuid)
            }
            taskRepository.saveOrUpdateCommonApps(modifiedList)
            val tagList = res.tagAppList.map { ti ->
                TagApp(ti.tagId, ti.tagName)
            }
            db.tagAppDao().insertAll(*tagList.toTypedArray())
            for (ti in res.tagAppList) {
                val tar = ti.hybridAppList.map { hybridAppList ->
                    if (!hybridAppList.appLogoUuid.isNullOrBlank()) {
                        val modifiedUuid = "https://st.yuexunit.com/fs/api/v1.0/viewPic.file?fileUuid=${hybridAppList.appLogoUuid}"
                        hybridAppList.copy(appLogoUuid = modifiedUuid)
                    } else {
                        hybridAppList.copy(appLogoUuid = "https://cdn.pixabay.com/photo/2023/11/21/21/38/puffins-8404284_1280.jpg")
                    }

                }
                db.hybridAppDao().insertAll(*tar.toTypedArray())
            }
        }
    }

    init {
        loadLocalData()
    }
}