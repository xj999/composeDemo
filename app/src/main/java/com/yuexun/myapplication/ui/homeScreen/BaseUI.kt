package com.yuexun.myapplication.ui.homeScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.yuexun.myapplication.R
import timber.log.Timber

@Composable
fun BasePlugApp(
    imgUrl: Any?,
    title: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    imgSize: Dp = 75.dp,
    fontSize: TextUnit = TextUnit.Unspecified,
    placeholder: Painter = painterResource(id = R.drawable.baseline_brightness_5_24)

) {
    SideEffect {
        Timber.e( "重组作用域 %s进行了重组",title)
    }
    Column(modifier = modifier.clickable { onClick() }, horizontalAlignment = Alignment.CenterHorizontally) {
        if (imgUrl is String && imgUrl.startsWith("http")) {
            AsyncImage(
                model = imgUrl,
                contentDescription = null,
                Modifier
                    .size(imgSize)
                    .padding(5.dp),
                placeholder = placeholder,
                contentScale = ContentScale.Crop
            )
        } else {

            Image(
                painter = if (imgUrl is Painter) imgUrl else placeholder,
                contentDescription = null,
                Modifier
                    .size(imgSize)
                    .padding(5.dp),
            )
        }
        Text(
            text = title,
            fontSize = fontSize,
            maxLines = 1
        )
    }
}

@Composable
@Preview(showBackground = true)
fun BtnStyle1() {
    BasePlugApp(
        imgUrl = painterResource(id = R.drawable.ic_icon_weixin),
        title = "test",
        onClick = {},
        fontSize = 14.sp,
        modifier = Modifier.height(130.dp),
        imgSize = 100.dp
    )
}