package com.yuexun.myapplication

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.yuexun.myapplication.ui.LColors
import com.yuexun.myapplication.ui.homeScreen.NewGridUI
import com.yuexun.myapplication.ui.theme.MyApplicationTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val homePageView: MainViewModels by viewModels()

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme(darkTheme = false) {
                Testss()
//                NewGridUI(homeState = homePageView.uiState(), onEvent = homePageView::onEvent)
            }
        }

    }


}

@Composable
fun Testss() {
    val ll: List<Int> = (0..200).toList()
    LazyVerticalGrid(columns = GridCells.Fixed(4), content = {
        items(ll) {
            Box(modifier = Modifier
                .background(LColors.Green.light)
                .fillMaxWidth()
                .height(75.dp), contentAlignment = Alignment.Center)
            {
                Text(text = it.toString())
            }


        }
    })
}






