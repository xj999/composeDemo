package com.yuexun.myapplication.ui.homeScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme.shapes
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.google.gson.Gson
import com.midai.data.db.entity.ApiResponseEntity
import com.midai.data.db.entity.TagWithHybridAppList
import com.yuexun.myapplication.MainViewModels
import com.yuexun.myapplication.R
import com.yuexun.myapplication.ui.LColors

@Composable
fun NewGridUI3(
    homeState: HomeState,
    onEvent: ((HomeEvent) -> Unit),
    modifier: Modifier = Modifier
) {
    val data = homeState.allApp.flatMap { it.hybridAppList }
    LazyVerticalGrid(
        columns = GridCells.Fixed(4), content = {
            items(data, key = {
                it.appId
            }, contentType = {
                it.type
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
        })
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(viewModel: MainViewModels = viewModel()) {
    val homeState = viewModel.uiState()
    val onEvent = viewModel::onEvent
    val bottomBtnList = listOf("grid渲染", "column渲染", "空页面", "未实现")
    val iconList = listOf(
        ImageVector.vectorResource(id = R.drawable.grid_view),
        ImageVector.vectorResource(id = R.drawable.table_rows),
        Icons.Default.Call,
        Icons.Default.Email
    )
    var selectIndex by remember {
        mutableIntStateOf(0)
    }
    Scaffold(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(LColors.Gray), topBar = {
            TopAppBar(title = {
                Text(text = homeState.tenantName)
            }, modifier = Modifier.shadow(6.dp), actions = {
                if (selectIndex == 0) {
                    Icon(
                        imageVector = ImageVector.vectorResource(id = R.drawable.qr_code_scanner),
                        contentDescription = null, modifier = Modifier.clickable {
                            onEvent(HomeEvent.OnScanClick)
                        }
                    )
                    Spacer(modifier = Modifier.width(15.dp))
                    Icon(Icons.Default.Add, contentDescription = null, Modifier.clickable {
                        onEvent(HomeEvent.OnScanClick)
                    })
                }

            }
            )
        }, bottomBar = {
            NavigationBar(
                modifier = Modifier
                    .fillMaxWidth()

            ) {
                bottomBtnList.forEachIndexed { index, s ->
                    NavigationBarItem(icon = {
                        Icon(imageVector = iconList[index], contentDescription = null)
                    }, label = {
                        Text(
                            text = s
                        )
                    }, selected = index == selectIndex, alwaysShowLabel = true,
                        onClick = {
                            selectIndex = index
                        }
                    )


                }
            }
        }, floatingActionButton = {
            FloatingActionButton(
                onClick = { onEvent(HomeEvent.OnNameBtnClick) },
                shape = CircleShape
            ) {
                Icon(Icons.Default.Add, contentDescription = "Add")
            }
        }
    ) {
        val state = rememberLazyGridState()
        when (selectIndex) {
            0 -> {
                Screen1(homeState, onEvent, state, modifier = Modifier.padding(it))
            }

            1 -> {
                HomeScreen(homeState, onEvent, modifier = Modifier.padding(it))
            }

            else -> {
                Box(
                    modifier = Modifier
                        .padding(it)
                        .fillMaxHeight()
                        .fillMaxWidth()
                        .background(LColors.Green.light), contentAlignment = Alignment.Center
                )
                {
                    Text("$selectIndex 页面开发中")
                }

            }
        }
    }
}

@Composable
fun Screen1(homeState: HomeState, onEvent: ((HomeEvent) -> Unit), state: LazyGridState, modifier: Modifier = Modifier) {
    LazyVerticalGrid(
        state = state,
        columns = GridCells.Fixed(4), content = {

            if (homeState.news.isNotEmpty()) {
                item(
                    span = { GridItemSpan(maxLineSpan) },
                    key = "top_banner",
                    contentType = "topBanner"
                ) {
                    AsyncImage(
                        model = "https://www.yuexunit.com/download/img/dzq.jpg",
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(300.dp)
                            .clip(shapes.medium),
                        contentScale = ContentScale.FillWidth,
                        placeholder = painterResource(id = R.drawable.baseline_qr_code_scanner_24)

                    )
                }
                item(span = { GridItemSpan(maxLineSpan) }) {
                    Spacer(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(15.dp)
                    )
                }
            }

//            item(span = { GridItemSpan(maxLineSpan) }, key = "top_bg", contentType = "bg") {
//                Box(
//                    modifier = Modifier
//                        .background(
//                            LColors.White, shape = RoundedCornerShape(
//                                topStart = 14.dp,
//                                topEnd = 14.dp,
//                                bottomEnd = 0.dp,
//                                bottomStart = 0.dp
//                            )
//                        )
//                        .fillMaxWidth()
//                        .height(20.dp)
//                ) {
//
//                }
//            }
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
                        .height(75.dp),
                    imgSize = 50.dp
                )
            }
            item(key = "SWITCH_BTN", contentType = "BTN") {
                AppSwitchBtn(switch = homeState.expanded, onClick = {
                    onEvent(HomeEvent.OnAppSwitchClick)
                })

            }
//            items(
//                when (homeState.myApp.size % 4) {
//                    0 -> 3
//                    1 -> 2
//                    2 -> 1
//                    else -> 0
//                }
//            ) {
//                Box(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .height(75.dp)
//                        .background(LColors.White)
//                )
//            }

            if (homeState.expanded) {
                homeState.allApp.forEach { it ->
                    item(
                        span = { GridItemSpan(maxLineSpan) },
                        key = it.tag.tagId,
                        contentType = "TAG_TITLE"
                    ) {
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
                                .height(75.dp),
                            imgSize = 50.dp
                        )
                    }
//                    items(
//                        when (it.hybridAppList.size % 4) {
//                            1 -> 3
//                            2 -> 2
//                            3 -> 1
//                            else -> 0
//                        }
//                    ) {
//                        Box(
//                            modifier = Modifier
//                                .fillMaxWidth()
//                                .height(75.dp)
//                                .background(LColors.White)
//                        )
//                    }
                }
            }
//            item(span = { GridItemSpan(maxLineSpan) }, key = "bottom_bg", contentType = "bg") {
//                Box(
//                    modifier = Modifier
//                        .background(
//                            LColors.White, shape = RoundedCornerShape(
//                                topStart = 0.dp,
//                                topEnd = 0.dp,
//                                bottomEnd = 16.dp,
//                                bottomStart = 16.dp
//                            )
//                        )
//                        .fillMaxWidth()
//                        .height(20.dp)
//                ) {
//
//                }
//            }

        }, modifier = modifier
            .padding(15.dp)
            .wrapContentHeight()
            .fillMaxWidth()
    )
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
        expanded = false
    )
    Screen1(tt, onEvent = {
    }, rememberLazyGridState(), modifier = Modifier)
}

