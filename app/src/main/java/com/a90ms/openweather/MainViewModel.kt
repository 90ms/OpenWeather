package com.a90ms.openweather

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
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
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getForecastListUseCase: GetForecastListUseCase
) : BaseViewModel() {

    private val _itemList = MutableLiveData<List<MainItem>>()
    val itemList: LiveData<List<MainItem>> get() = _itemList

    private val list = mutableListOf<MainItem>()

    suspend fun fetch() = viewModelScope.launch {
        val seoulDeferred = async { fetchForecast(cityList[0]) }
        val londonDeferred = async { fetchForecast(cityList[1]) }
        val chicagoDeferred = async { fetchForecast(cityList[2]) }

        seoulDeferred.await()
        londonDeferred.await()
        chicagoDeferred.await()
    }

    private fun fetchForecast(city: City) = viewModelScope.launch {
        getForecastListUseCase(city).onSuccess {
            list.add(MainItem.Header(city.name))
            it.manufactureList().forEach {
                list.add(
                    MainItem.Weather(
                        it.copy(
                            shortDate = it.shortDate + "(${city.name})"
                        )
                    )
                )
            }
            _itemList.value = list
        }.onError { code, message ->
            Timber.e("onError(${city.name}) => $code / $message")
        }.onException {
            Timber.e("onException(${city.name}) => ${it.message}")
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
