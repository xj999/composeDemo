package com.yuexun.myapplication.ui.homeScreen

import androidx.compose.runtime.Immutable
import com.midai.data.db.entity.HybridApp
import com.midai.data.db.entity.TagWithHybridAppList

@Immutable
data class HomeState(

    var tenantName: String = "",

    val tenantSwitch: Boolean = false,

    val myApp: List<HybridApp> = emptyList(),

    val allApp: List<TagWithHybridAppList> = emptyList(),

    val news: List<String> = emptyList(),

    val todoCenterMessage: List<String> = emptyList(),

    val myNotice: List<String> = emptyList(),

    val expanded: Boolean = false


)
