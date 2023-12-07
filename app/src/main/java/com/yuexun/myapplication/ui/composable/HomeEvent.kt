package com.yuexun.myapplication.ui.composable


sealed interface HomeEvent {
    data object OnPreviousMonth : HomeEvent
    data object OnNameBtnClick : HomeEvent
}