package com.yuexun.myapplication.ui.homeScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme.shapes
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.google.gson.Gson
import com.midai.data.db.entity.ApiResponseEntity
import com.midai.data.db.entity.TagWithHybridAppList
import com.yuexun.myapplication.R
import com.yuexun.myapplication.ui.LColors


@Composable
fun NewGridUI(homeState: HomeState, onEvent: ((HomeEvent) -> Unit), modifier: Modifier = Modifier) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(LColors.Gray)
    )
    {
        val appPlaceholder = remember {
            mutableIntStateOf(0)
        }
        LaunchedEffect(Unit)
        {
        }
        LazyVerticalGrid(
            columns = GridCells.Fixed(4), content = {

                if (homeState.news.isNotEmpty()) {
                    item(span = { GridItemSpan(maxLineSpan) }, key = "top_banner", contentType = "topBanner") {
                        AsyncImage(
                            model = "https://www.yuexunit.com/download/img/dzq.jpg",
                            contentDescription = null,
                            modifier= Modifier
                                .fillMaxWidth()
                                .height(300.dp)
                                .clip(shapes.medium),
                            contentScale = ContentScale.FillWidth,
                            placeholder = painterResource(id = R.drawable.baseline_qr_code_scanner_24)

                        )
                    }
                    item(span = {GridItemSpan(maxLineSpan)}){
                        Spacer(modifier = Modifier.fillMaxWidth().height(15.dp))
                    }
                }

                item(span = { GridItemSpan(maxLineSpan) }, key = "top_bg", contentType = "bg") {
                    Box(
                        modifier = Modifier
                            .background(
                                LColors.White, shape = RoundedCornerShape(
                                    topStart = 14.dp,
                                    topEnd = 14.dp,
                                    bottomEnd = 0.dp,
                                    bottomStart = 0.dp
                                )
                            )
                            .fillMaxWidth()
                            .height(20.dp)
                    ) {

                    }
                }
                items(homeState.myApp, key = { app ->
                    "MYAPP_${app.appKey}"
                }, contentType = {
                    "APP"
                }) { app ->
                    BasePlugApp(
                        imgUrl = app.appLogoUuid,
                        title = app.appName,
                        onClick = { onEvent(HomeEvent.OnAppItemClick(app)) },
                        fontSize = 14.sp,
                        modifier = Modifier
                            .height(75.dp)
                            .background(LColors.White),
                        imgSize = 50.dp
                    )
                }
                item(key = "SWITCH_BTN", contentType = "BTN") {
                    AppSwitchBtn(switch = homeState.expanded, onClick = {
                        onEvent(HomeEvent.OnAppSwitchClick)
                    })

                }
                items(
                    when (homeState.myApp.size % 4) {
                        0 -> 3
                        1 -> 2
                        2 -> 1
                        else -> 0
                    }
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(75.dp)
                            .background(LColors.White)
                    )
                }

                if (homeState.expanded) {
                    homeState.allApp.forEach { it ->
                        item(span = { GridItemSpan(maxLineSpan) }, key = it.tag.tagId, contentType = "TAG_TITLE") {
                            CategoryHeader(it.tag)
                        }
                        items(it.hybridAppList, key = { app ->
                            "APP${app.appKey}"
                        }, contentType = {
                            "APP"
                        }) { app ->
                            BasePlugApp(
                                imgUrl = app.appLogoUuid,
                                title = app.appName,
                                onClick = { onEvent(HomeEvent.OnAppItemClick(app)) },
                                fontSize = 14.sp,
                                modifier = Modifier
                                    .height(75.dp)
                                    .background(LColors.White),
                                imgSize = 50.dp
                            )
                        }
                        items(
                            when (it.hybridAppList.size % 4) {
                                1 -> 3
                                2 -> 2
                                3 -> 1
                                else -> 0
                            }
                        ) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(75.dp)
                                    .background(LColors.White)
                            )
                        }
                    }
                }
                item(span = { GridItemSpan(maxLineSpan) }, key = "bottom_bg", contentType = "bg") {
                    Box(
                        modifier = Modifier
                            .background(
                                LColors.White, shape = RoundedCornerShape(
                                    topStart = 0.dp,
                                    topEnd = 0.dp,
                                    bottomEnd = 16.dp,
                                    bottomStart = 16.dp
                                )
                            )
                            .fillMaxWidth()
                            .height(20.dp)
                    ) {

                    }
                }

            }, modifier = Modifier
                .padding(15.dp)
                .wrapContentHeight()
                .fillMaxWidth()
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

