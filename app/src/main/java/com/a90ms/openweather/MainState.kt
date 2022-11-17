package com.a90ms.openweather

sealed class MainState {
    data class OnError(val msg: String) : MainState()
}
