@file:OptIn(ExperimentalGlideComposeApi::class)

package com.yuexun.myapplication.ui.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraintsScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.yuexun.myapplication.data.db.entity.CommonApp
import com.yuexun.myapplication.data.db.entity.HybridApp
import com.yuexun.myapplication.ui.LColors
import timber.log.Timber

@Composable
fun MyApp(app: CommonApp) {
    Column(
        Modifier
            .fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally
    ) {
        GlideImage(
            model = app.appLogoUuid,
            contentDescription = null,
            Modifier.size(50.dp)
        )
        Text(text = app.appName, fontSize = 16.sp)
    }
}

@Composable
fun HybridAppUi(app: HybridApp) {
    Column(
        Modifier
            .fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally
    ) {
        GlideImage(
            model = app.appLogoUuid,
            contentDescription = null,
            Modifier.size(50.dp)
        )
        Text(text = app.appName, fontSize = 16.sp)
    }
}

@Composable
fun HomeScreen(homeState: HomeState, onBufferClick: () -> Unit, modifier: Modifier = Modifier) {
    val app by rememberUpdatedState(newValue = homeState.myApp.collectAsState().value)
    val tenantName by remember {
        mutableStateOf(homeState.tenantName)
    }
    Box(
        modifier = Modifier
            .background(LColors.Purple.extraLight)
            .fillMaxHeight()
            .fillMaxWidth()
    )
    {
        AppGrid(app)
        ElevatedButton(
            onClick = onBufferClick, modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp)
                .wrapContentWidth()
                .wrapContentHeight()
        )

        Text(text = tenantName,modifier = Modifier
            .align(Alignment.TopStart)
            .padding(16.dp)
            .wrapContentWidth()
            .wrapContentHeight())
    }

}

@Composable
fun ElevatedButton(onClick: () -> Unit, modifier: Modifier = Modifier) {
    ElevatedButton(onClick = { onClick() }, modifier) {
        Text("change tenantName")
    }
}

@Composable
fun AppGrid(apps: List<CommonApp>) {
    val groupedApps = apps.groupBy { it.tagId }
    LazyVerticalGrid(
        columns = GridCells.Fixed(4),
        modifier = Modifier.padding(0.dp,30.dp,0.dp,0.dp)
    ) {
        groupedApps.forEach { (category, appList) ->
            item(span = { GridItemSpan(maxLineSpan) }) {
                CategoryHeader(category)
            }
            items(appList) { app ->
                MyApp(app)
            }
        }
    }

}

@Composable
fun CategoryHeader(category: String) {
    val categoryText = when (category) {
        "1" -> "我的应用"
        "2" -> "后勤管理"
        "3" -> "办公"
        else -> "其他"
    }
    Text(
        text = categoryText,
        fontWeight = FontWeight.Bold,
        fontSize = 16.sp,
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    )
}
