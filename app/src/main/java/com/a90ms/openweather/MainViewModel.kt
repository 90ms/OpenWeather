package com.a90ms.openweather

import androidx.lifecycle.viewModelScope
import com.a90ms.domain.base.onError
import com.a90ms.domain.base.onException
import com.a90ms.domain.base.onSuccess
import com.a90ms.domain.data.dto.ListDto
import com.a90ms.domain.data.entity.city.City
import com.a90ms.domain.usecase.GetForecastListUseCase
import com.a90ms.openweather.base.BaseViewModel
import com.a90ms.openweather.data.cityList
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getForecastListUseCase: GetForecastListUseCase
) : BaseViewModel() {

    fun fetch() {
        viewModelScope.launch {
            cityList.forEach { fetchForecast(it) }
        }
    }

    private suspend fun fetchForecast(city: City) {
        getForecastListUseCase(city).onSuccess {
            Timber.d("onSuccess(${city.name}) => ${it.manufactureList()}")
        }.onError { code, message ->
            Timber.e("onError => $code / $message")
        }.onException {
            Timber.e("onException => ${it.message}")
        }
    }

    private fun List<ListDto>.manufactureList() = groupBy {
        it.shortDate
    }.map { entry ->
        val max = entry.value.maxBy { it.main.temp_max }
        val min = entry.value.minBy { it.main.temp_min }
        entry.key to entry.value.map { dto ->
            dto.copy(
                main = dto.main.copy(
                    temp_max = max.main.temp_max,
                    temp_min = min.main.temp_min
                )
            )
        }.distinctBy {
            it.shortDate
        }
    }.map {
        it.second[0]
    }
}