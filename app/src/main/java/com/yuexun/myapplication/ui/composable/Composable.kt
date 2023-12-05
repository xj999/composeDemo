package com.yuexun.myapplication.ui.composable

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun LeftImgText(text: String, iconRes: Int?) {
    Row(
        modifier = Modifier
            .height(45.dp)
            .wrapContentWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (iconRes != null) {
            Image(
                painter = painterResource(id = iconRes),
                contentDescription = "Drawable",
                modifier = Modifier
                    .size(20.dp)
            )
        }


        Text(
            text = text,
            color = Color.Gray,
            fontSize = 16.sp,
            modifier = Modifier.padding(start = 8.dp)
        )
    }
}

@Composable
fun ItemRow(text: String, iconRes: Int?, showIcon: Boolean, onClick: () -> Unit) {
    var clicked by remember { mutableStateOf(false) }
    Row(
        modifier = Modifier
            .height(45.dp)
            .fillMaxWidth()
            .background(
                color = Color.White,
                shape = RoundedCornerShape(0.dp)
            )
            .clickable(onClick = {
                clicked = !clicked
                onClick()
            })
            .padding(start = 8.dp, end = 8.dp),
        verticalAlignment = Alignment.CenterVertically
//        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        if (iconRes != null) {
            Image(
                painter = painterResource(id = iconRes),
                contentDescription = "Drawable",
                modifier = Modifier
                    .size(20.dp)
                    .weight(1f)
            )
        }

        Text(
            text = text,
            color = Color.Gray,
            fontSize = 16.sp,
            modifier = Modifier.padding(start = 8.dp).weight(8f).background(Color.Blue)
        )
        Icon(
            imageVector = Icons.Default.ArrowForward,
            contentDescription = "Forward Arrow",
            tint = Color.Gray,
            modifier = if (showIcon) Modifier.weight(1f) else Modifier.size(0.dp).weight(1f)
        )
    }
}
