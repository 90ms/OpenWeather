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
import javax.inject.Inject
import kotlinx.coroutines.launch
import timber.log.Timber

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getForecastListUseCase: GetForecastListUseCase
) : BaseViewModel() {

    private val _itemList = MutableLiveData<List<MainItem>>()
    val itemList: LiveData<List<MainItem>> get() = _itemList

    private val _state = MutableLiveData<MainState>()
    val state: LiveData<MainState> get() = _state

    private val list = mutableListOf<MainItem>()

    suspend fun fetch() = viewModelScope.launch {
        list.clear()
        cityList.forEach {
            fetchForecast(it).join()
        }
        _state.value = MainState.OnCompleteFetch
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
            list.add(MainItem.Header(city.name + "[조회 실패]"))
            _itemList.value = list
            Timber.e("onError(${city.name}) => $code / $message")
        }.onException {
            list.add(MainItem.Header(city.name + "[조회 실패]"))
            _itemList.value = list
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
