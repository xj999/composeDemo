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
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.midai.data.db.entity.CommonApp
import com.midai.data.db.entity.HybridApp
import com.midai.data.db.entity.TagApp
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
fun MyApp(app: com.midai.data.db.entity.CommonApp, onAppItemClick: (Any) -> Unit) {
    Column(
        Modifier
            .fillMaxWidth()
            .height(75.dp)
            .clickable { onAppItemClick(app) }, horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AsyncImage(
            model = app.appLogoUuid,
            contentDescription = null,
            Modifier
                .size(50.dp)
                .padding(5.dp),
            placeholder = painterResource(id = R.drawable.baseline_brightness_5_24)
        )
        Text(text = app.appName, fontSize = 16.sp, maxLines = 1)
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
fun HomeScreen(homeState: HomeState, onEvent: (HomeEvent) -> Unit, modifier: Modifier = Modifier) {
    Box(
        modifier = Modifier
            .background(LColors.White)
            .fillMaxHeight()
            .fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .background(LColors.White)
                .fillMaxWidth()
        )
        {
            TitleBar(
                showName = homeState.tenantName,
                tenantSwitch = false,
                onTenantSwitchClick = {},
                onScanBtnClick = {})

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
fun ElevatedButton(onClick: () -> Unit, modifier: Modifier = Modifier) {
    ElevatedButton(onClick = { onClick() }, modifier) {
        Text("change Title")
    }
}

@Composable
fun AppGrid(
    apps: List<CommonApp>,
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
            ElevatedCard(
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 6.dp
                ),
                modifier = Modifier
                    .padding(10.dp, 15.dp, 10.dp, 10.dp)
                    .background(LColors.White)
            ) {
                val rowHeight = 75
                val itemsPerRow = 4
                val hybridHeight = hybridApps.sumOf {
                    val itemCount = it.hybridAppList.size
                    val rows = (itemCount + itemsPerRow - 1) / itemsPerRow
                    rows * rowHeight + 35
                }
                val appHeight = ((apps.size + 1 + itemsPerRow - 1) / itemsPerRow)*75
                Timber.e("hybridHeight %s appHeight = %s",hybridHeight,appHeight)
                LazyVerticalGrid(
                    columns = GridCells.Fixed(4),
                    modifier = Modifier
                        .padding(10.dp)
                        .fillMaxWidth()
                        .height(if (apps.isEmpty()) hybridHeight.dp else (hybridHeight + appHeight).dp)
                ) {
                    if (apps.isNotEmpty()) {
                        items(apps) { app ->
                            MyApp(app, onAppItemClick)
                        }
                        item {
                            AppSwitchBtn(switch = appSwitch, onClick = {
                                onSwitchClick()
                            })
                        }

                    }

                    if (hybridApps.isNotEmpty() && appSwitch) {
                        hybridApps.forEach {
                            item(span = { GridItemSpan(maxLineSpan) }) {
                                CategoryHeader(it.tag)
                            }
                            items(it.hybridAppList) { app ->
                                HybridAppUi(app, onAppItemClick)
                            }
                        }

                    }
                }
            }
        }

    }


}

@Composable
fun CategoryHeader(tag: TagApp) {
    Text(
        text = tag.tagName,
        fontWeight = FontWeight.Bold,
        fontSize = 16.sp,
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .height(30.dp)
    )
}
