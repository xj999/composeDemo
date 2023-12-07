package com.yuexun.myapplication.ui.composable

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import com.yuexun.myapplication.data.db.entity.CommonApp
import com.yuexun.myapplication.data.db.entity.HybridApp
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.emptyFlow

@Immutable
data class HomeState(

    var tenantName: String = "",

    val tenantSwitch: Boolean = false,


    val myApp: MutableStateFlow<List<CommonApp>> = MutableStateFlow(emptyList()),

    val allApp: List<HybridApp> = emptyList(),

    val news: List<String> = emptyList(),

    val todoCenterMessage: List<String> = emptyList(),

    val myNotice: List<String> = emptyList(),

    val expanded: Boolean = false


)