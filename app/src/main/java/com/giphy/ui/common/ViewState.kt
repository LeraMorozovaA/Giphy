package com.giphy.ui.common

sealed class ViewState<out T> {
    object Idle : ViewState<Nothing>()
    object Loading : ViewState<Nothing>()
    object Success : ViewState<Nothing>()
    class Error<T>(val error: Exception) : ViewState<T>()
    class Data<T>(val data: T) : ViewState<T>()

}