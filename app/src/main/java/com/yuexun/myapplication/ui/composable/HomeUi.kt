@file:OptIn(ExperimentalGlideComposeApi::class)

package com.yuexun.myapplication.ui.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage

@Composable
fun App(app: AppData) {
    Column(
        Modifier
            .fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally
    ) {
        GlideImage(
            model = app.picUrl,
            contentDescription = null,
            Modifier.size(50.dp)
        )
        Text(text = app.appName, fontSize = 16.sp)
    }
}

@Composable
fun AppGrid(apps: List<AppData>) {
    val appList by remember { mutableStateOf(apps) }

    val groupedApps = appList.groupBy { it.appType }

    LazyVerticalGrid(
        columns = GridCells.Fixed(4)
    ) {
        groupedApps.forEach { (category, appList) ->
            item(span = { GridItemSpan(maxLineSpan) }) {
                CategoryHeader(category)
            }
            items(appList) { app ->
                App(app)
            }
        }
    }

}

@Composable
fun CategoryHeader(category: Int) {
    val categoryText = when (category) {
        1 -> "我的应用"
        2 -> "后勤管理"
        3 -> "办公"
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
