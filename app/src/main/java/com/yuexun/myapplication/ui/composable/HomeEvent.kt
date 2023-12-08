package com.yuexun.myapplication.ui.composable


sealed interface HomeEvent {
    data object OnNameBtnClick : HomeEvent
    data object OnAppSwitchClick : HomeEvent

    data class OnAppItemClick(val app: Any) : HomeEvent

}