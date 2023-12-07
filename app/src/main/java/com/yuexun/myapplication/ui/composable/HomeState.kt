package com.yuexun.myapplication.ui.composable

import androidx.compose.runtime.Immutable
import com.yuexun.myapplication.data.db.entity.CommonApp
import com.yuexun.myapplication.data.db.entity.HybridApp
import kotlinx.coroutines.flow.MutableStateFlow

@Immutable
data class HomeState(

    var tenantName: String = "",

    val tenantSwitch: Boolean = false,

    val myApp: List<CommonApp> = emptyList(),

    val allApp: List<HybridApp> = emptyList(),

    val news: List<String> = emptyList(),

    val todoCenterMessage: List<String> = emptyList(),

    val myNotice: List<String> = emptyList(),

    val expanded: Boolean = false


)