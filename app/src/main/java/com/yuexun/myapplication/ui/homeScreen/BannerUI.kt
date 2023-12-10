package com.yuexun.myapplication.ui.homeScreen

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage

@Composable
fun LBanner(bannerData:List<String>)
{

    AsyncImage(
        model = "https://i0.hdslb.com/bfs/article/7c54bfaa2dcd8d1eecd59f727a18cb7da02aa1e0.jpg@1256w_838h_!web-article-pic.webp",
        contentDescription = "banner img",
        modifier = Modifier
            .fillMaxWidth()
            .height(250.dp)
            .padding(0.dp, 15.dp, 0.dp, 0.dp)
    )
}