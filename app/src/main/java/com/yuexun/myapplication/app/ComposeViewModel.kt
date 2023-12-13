package com.yuexun.myapplication.app

import androidx.compose.runtime.Composable
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import timber.log.Timber

/**
 * A simple base ViewModel utilizing Compose' reactivity.
 */
abstract class ComposeViewModel<UiState, UiEvent> : ViewModel() {
    /**
     * Optimized for Compose ui state.
     * Use only Compose primitives and immutable structures.
     * @return optimized for Compose ui state.
     */
    @Composable
    abstract fun uiState(): UiState

    /**
     * Sends an event of an action that happened
     * in the UI to be processed in the ViewModel.
     */
    abstract fun onEvent(event: UiEvent)

    val workErrorH: MutableLiveData<Boolean> = MutableLiveData()

    val errorHandler = CoroutineExceptionHandler { _, exception ->
        Timber.e("CoroutineExceptionHandler got $exception")
        workErrorH.postValue(false)
    }

    val workErrorMessage: MutableLiveData<String> = MutableLiveData()
    val errorHandlerMessage = CoroutineExceptionHandler { _, exception ->
        workErrorMessage.postValue(exception.message)
    }
}