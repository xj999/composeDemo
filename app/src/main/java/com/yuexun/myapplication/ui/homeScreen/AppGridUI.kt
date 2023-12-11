package com.yuexun.myapplication.ui.homeScreen

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.midai.data.db.entity.AppType
import com.midai.data.db.entity.HybridApp
import com.midai.data.db.entity.TagApp
import com.midai.data.db.entity.TagWithHybridAppList
import com.yuexun.myapplication.R
import timber.log.Timber


@Composable
fun MyAppGridColumn(
    modifier: Modifier,
    rowCount: Int,
    content: List<HybridApp>,
    hybridApps: List<TagWithHybridAppList>,
    appSwitch: Boolean,
    onAppItemClick: (Any) -> Unit,
    onSwitchClick: () -> Unit
) {
    Column(
        modifier = modifier.animateContentSize(),
        verticalArrangement = Arrangement.spacedBy(4.dp),
    ) {
        content.chunked(rowCount).forEach { row ->
            MyAppGridRow(rowCount, row, appSwitch, onAppItemClick, onSwitchClick, AppType.MYAPP)
        }
        if (content.isNotEmpty() && content.size % rowCount == 0) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .height(75.dp)
                ) {
                    AppSwitchBtn(switch = appSwitch, onClick = onSwitchClick)
                }

                val remainingCount = rowCount - 1
                repeat(remainingCount) {
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .height(75.dp)
                    ) {}
                }
            }
        }
        if (appSwitch) {
            hybridApps.forEach {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .height(35.dp)
                    )
                    {
                        CategoryHeader(tag = it.tag)
                    }
                }
                it.hybridAppList.chunked(rowCount).forEach { row ->

                    MyAppGridRow(rowCount, row, appSwitch, onAppItemClick, onSwitchClick, AppType.HybridApp)
                }

            }


        }


    }

}

@Composable
private fun MyAppGridRow(
    rowCount: Int,
    row: List<HybridApp>,
    appSwitch: Boolean,
    onAppItemClick: (Any) -> Unit,
    onSwitchClick: () -> Unit = {},
    type: AppType
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        row.forEach {
                AppItem(
                    modifier = Modifier
                        .weight(1f)
                        .height(75.dp)
                        .clickable { onAppItemClick(it) }, app = it
                )
        }

        var nowSize = row.size
        if (row.size < rowCount && type == AppType.MYAPP) {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .height(75.dp)
            ) {
                AppSwitchBtn(switch = appSwitch, onClick = onSwitchClick)
            }
            nowSize++
        }
        val remainingCount = rowCount - nowSize
        repeat(remainingCount) {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .height(75.dp)
            ) {}
        }

    }
}


@Composable
fun AppItem(modifier: Modifier, app: HybridApp) {
    LaunchedEffect(Unit){
        Timber.e(app.appKey)
    }
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally

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
fun CategoryHeader(tag: TagApp) {
    Text(
        text = tag.tagName,
        fontSize = 16.sp,
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp)
            .fillMaxHeight()
    )
}