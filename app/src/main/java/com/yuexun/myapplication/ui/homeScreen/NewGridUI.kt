package com.yuexun.myapplication.ui.homeScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.gson.Gson
import com.midai.data.db.entity.ApiResponseEntity
import com.midai.data.db.entity.HybridApp
import com.midai.data.db.entity.TagWithHybridAppList
import com.yuexun.myapplication.ui.LColors
import timber.log.Timber


@Composable
fun NewGridUI(homeState: HomeState, onEvent: ((HomeEvent) -> Unit), modifier: Modifier = Modifier) {
    LazyVerticalGrid(columns = GridCells.Fixed(4), content = {
        itemsIndexed(items = homeState.allApp.flatMap { it.hybridAppList }) { index, item ->
            myItem(
                modifier = Modifier
                    .height(75.dp)
//                    .background(LColors.White, shape = if (index==0) RoundedCornerShape(topStart = 16.dp, topEnd = 0.dp, bottomStart = 0.dp, bottomEnd = 0.dp) else RoundedCornerShape(topStart = 0.dp, topEnd = 16.dp, bottomStart = 0.dp, bottomEnd = 0.dp))
                    .shadow(elevation =if (index==0) 1.dp else 0.dp, shape = if (index==0) RoundedCornerShape(topStart = 16.dp, topEnd = 0.dp, bottomStart = 0.dp, bottomEnd = 0.dp) else RoundedCornerShape(topStart = 0.dp, topEnd = 16.dp, bottomStart = 0.dp, bottomEnd = 0.dp))
                    .clip(RoundedCornerShape(16.dp))
//                    .border(1.dp,LColors.DarkGray)
                    .padding(5.dp),
                data = item,
                index = index
            )

        }
    }, modifier = Modifier.background(LColors.White).padding(15.dp))
}


@Composable
fun myItem(modifier: Modifier = Modifier, data: HybridApp, index: Int) {
    LaunchedEffect(Unit)
    {
        Timber.e("渲染了 %s 当前index %s", data.appName, index)
    }
    Box(modifier = modifier) {
        Text(
            text = data.appName, modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.Center)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun NewGridUI2() {
    val jsonString = remember {
        jsonData.plugdata
    }

    val previewData = remember {
        Gson().fromJson(jsonString, ApiResponseEntity::class.java)
    }

    val tagWithHybridAppList: List<TagWithHybridAppList> = previewData.tagAppList.map { tagApp ->
        TagWithHybridAppList(tagApp, tagApp.hybridAppList)
    }
    val tt = HomeState(
        tenantName = "单位名称",
        false,
        myApp = previewData.commonAppList,
        allApp = tagWithHybridAppList,
        emptyList(),
        listOf("3", "4"),
        listOf("5", "6"),
        expanded = true
    )
    NewGridUI(tt, onEvent = { homeEvent ->
        // 在这里处理 HomeEvent 对象
        when (homeEvent) {
            is HomeEvent.OnAppItemClick -> {
                // 处理 OnAppItemClick 事件
                val appItem = homeEvent.app
                // 执行其他逻辑
            }
            // 处理其他事件...
            else -> {}
        }
    }, modifier = Modifier)
}