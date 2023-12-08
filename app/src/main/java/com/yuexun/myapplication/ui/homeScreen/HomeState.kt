package com.yuexun.myapplication.ui.homeScreen

import androidx.compose.runtime.Immutable
import com.midai.data.db.entity.CommonApp
import com.midai.data.db.entity.TagWithHybridAppList

@Immutable
data class HomeState(

    var tenantName: String = "",

    val tenantSwitch: Boolean = false,

    val myApp: List<com.midai.data.db.entity.CommonApp> = emptyList(),

    val allApp: List<com.midai.data.db.entity.TagWithHybridAppList> = emptyList(),

    val news: List<String> = emptyList(),

    val todoCenterMessage: List<String> = emptyList(),

    val myNotice: List<String> = emptyList(),

    val expanded: Boolean = false


)
