package com.yuexun.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.yuexun.myapplication.ui.composable.App
import com.yuexun.myapplication.ui.composable.AppData
import com.yuexun.myapplication.ui.composable.AppGrid
import com.yuexun.myapplication.ui.composable.ItemRow
import com.yuexun.myapplication.ui.theme.MyApplicationTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MyApplicationTheme(darkTheme = false) {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize()) {

                    MyComposeScreen()

                }

            }
        }
    }
}

@Composable
fun MyComposeScreen() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
//        ItemRow(text = "我的单位", iconRes = R.drawable.ic_my_company_2, showIcon = false) {
//            // 处理点击事件
//        }


        val list = mutableListOf<AppData>()
        for (i in 1..5) {
            list.add(AppData("通知管理", 2, "https://www.pokemon.cn/play/resources/pokedex/img/pm/cf47f9fac4ed3037ff2a8ea83204e32aff8fb5f3.png",1))
            list.add(AppData("公文流转", 2, "https://www.pokemon.cn/play/resources/pokedex/img/pm/d0ee81f16175c97770192fb691fdda8da1f4f349.png",2))
            list.add(AppData("审批", 2, "https://www.pokemon.cn/play/resources/pokedex/img/pm/5794f0251b1180998d72d1f8568239620ff5279c.png",3))
            list.add(AppData("请假系统", 2, "https://www.pokemon.cn/play/resources/pokedex/img/pm/2b3f6ff00db7a1efae21d85cfb8995eaff2da8d8.png",4))

        }
            AppGrid(list)

//        ItemRow("绑定手机", R.drawable.ic_modify_phone, true) {
//            //todo click action
//        }
//        ItemRow("修改密码", R.drawable.icon_mine_item_2, false) {
//            //todo click action
//        }
//        ItemRow("绑定微信", R.drawable.ic_icon_weixin, true) {
//            //todo click action
//        }
//        ItemRow("班级管理", R.drawable.ic_icon_banjiguanli, true) {
//            //todo click action
//        }
//        ItemRow("常用连接", R.drawable.link_icon, true) {
//            //todo click action
//        }
//        ItemRow("应用分享", R.drawable.icon_share, true) {
//            //todo click action
//        }
//        ItemRow("设置", R.drawable.icon_mine_item_6, true) {
//            //todo click action
//        }
//    }
    }
}

@Preview(showBackground = false)
@Composable
fun GreetingPreview() {
    MyApplicationTheme(darkTheme = false) {
        MyComposeScreen()
    }
}





