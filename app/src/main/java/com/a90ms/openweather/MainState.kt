package com.a90ms.openweather

sealed class MainState {
    object OnCompleteFetch : MainState()
}
