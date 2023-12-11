package com.yuexun.myapplication.ui.homeScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.gson.Gson
import com.midai.data.db.entity.ApiResponseEntity
import com.midai.data.db.entity.HybridApp
import com.midai.data.db.entity.TagWithHybridAppList
import com.yuexun.myapplication.R
import com.yuexun.myapplication.ui.LColors
import timber.log.Timber


@Composable
fun AppSwitchBtn(switch: Boolean, onClick: () -> Unit) {
    Column(
        Modifier
            .fillMaxWidth()
            .clickable { onClick() }, horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (switch) {
            Image(
                painter = painterResource(R.mipmap.retact_app_icon),
                contentDescription = "Contact profile picture",
                Modifier
                    .size(50.dp)
                    .padding(5.dp)
            )
            Text(text = "收起", fontSize = 16.sp)
        } else {
            Image(
                painter = painterResource(R.mipmap.open_app_icon),
                contentDescription = "Contact profile picture",
                modifier = Modifier
                    .size(50.dp)
                    .padding(5.dp)
//                    .clip(CircleShape)
            )
            Text(text = "全部应用", fontSize = 16.sp)
        }
    }

}


@Composable
fun HybridAppUi(app: HybridApp, onAppItemClick: (Any) -> Unit) {
    Column(
        Modifier
            .fillMaxWidth()
            .padding(5.dp)
            .height(75.dp)
            .clickable { onAppItemClick(app) }, horizontalAlignment = Alignment.CenterHorizontally

    ) {
//        GlideImage(
//            model = app.appLogoUuid,
//            contentDescription = null,
//            Modifier.size(50.dp)
//        )
        Image(
            painter = painterResource(R.drawable.baseline_qr_code_scanner_24),
            contentDescription = "Contact profile picture", Modifier.size(50.dp)
        )
        Text(
            text = app.appName.trim(),
            fontSize = 16.sp,
            maxLines = 1,
            modifier = Modifier.align(CenterHorizontally)
        )
    }
}

@Composable
fun HomeScreen(homeState: HomeState, onEvent: ((HomeEvent) -> Unit), modifier: Modifier = Modifier) {
    Scaffold (topBar = {
        TitleBar(
            showName = homeState.tenantName,
            tenantSwitch = false,
            onTenantSwitchClick = {},
            onScanBtnClick = {})
    }, floatingActionButton = {
        FloatingActionButton(onClick = { onEvent(HomeEvent.OnNameBtnClick) }, shape = CircleShape, modifier = Modifier.background(LColors.Blue.extraLight)) {
            Icon(Icons.Default.Add, contentDescription = "Add",modifier=Modifier.background(LColors.Blue.extraLight))
        }
    }){
        Column(
            modifier = Modifier
                .background(LColors.White)
                .fillMaxWidth()
                .padding(it)
        )
        {
            AppGrid(homeState.myApp, homeState.allApp, homeState.news,
                homeState.expanded, onSwitchClick = { onEvent(HomeEvent.OnAppSwitchClick) },
                onAppItemClick = { onEvent(HomeEvent.OnAppItemClick(it)) })
        }

    }

}

@Composable
fun TitleBar(
    showName: String,
    tenantSwitch: Boolean,
    onTenantSwitchClick: () -> Unit,
    onScanBtnClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .background(LColors.Blue.light)
            .padding(5.dp)
            .height(45.dp)
            .fillMaxWidth()
    )
    {
        Text(
            text = showName, modifier = Modifier
                .align(Alignment.CenterStart)
                .wrapContentWidth()
                .wrapContentHeight()
        )

        Image(
            painter = painterResource(R.drawable.baseline_qr_code_scanner_24),
            contentDescription = "Contact profile picture",
            Modifier
                .size(35.dp)
                .align(Alignment.CenterEnd)
                .clickable { onScanBtnClick() }

        )

    }

}



@Composable
fun AppGrid(
    apps: List<HybridApp>,
    hybridApps: List<TagWithHybridAppList>,
    newsData: List<String>,
    appSwitch: Boolean,
    onSwitchClick: () -> Unit,
    onAppItemClick: (Any) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
    ) {

        if (newsData.isNotEmpty()) {
            item { LBanner(bannerData = newsData) }
        }
        item {
            Card(
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 6.dp
                ),
                modifier = Modifier
                    .padding(10.dp, 10.dp, 10.dp, 10.dp)

            ) {
                MyAppGridColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .background(LColors.White),
                    rowCount = 4,
                    content = apps,
                    hybridApps = hybridApps,
                    appSwitch,
                    onAppItemClick = onAppItemClick,
                    onSwitchClick = onSwitchClick
                )

            }
        }
        item {
            // TODO:  待办模块
             Box(modifier = Modifier
                 .fillMaxWidth()
                 .height(500.dp))
             {
                Timber.e("---------------- todo ")
             }
        }
        item {
            // TODO: 通知模块
        }

    }


}



@Preview(showBackground = true)
@Composable
fun HomeScreen2()
{
    val jsonString = remember {
        jsonData.plugdata
    }

    val previewData = remember {
        Gson().fromJson(jsonString, ApiResponseEntity::class.java)
    }

    val tagWithHybridAppList: List<TagWithHybridAppList> = previewData.tagAppList.map { tagApp ->
        TagWithHybridAppList(tagApp, tagApp.hybridAppList)
    }
    val tt=HomeState(
        tenantName = "单位名称",
        false,
        myApp = previewData.commonAppList,
        allApp = tagWithHybridAppList,
        emptyList(),
        listOf("3", "4"),
        listOf("5", "6"),
        expanded = true
    )
    HomeScreen(tt, onEvent = { homeEvent ->
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

