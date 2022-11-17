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
    }

    private suspend fun fetchForecast(city: City) = viewModelScope.launch {
        showLoading()
        getForecastListUseCase(city).onSuccess {
            list.add(MainItem.Header(city.name))

            val responseList = it.manufactureList()

            responseList.forEachIndexed { index, listDto ->
                list.add(MainItem.Weather(listDto))
                if (index < responseList.size - 1) {
                    list.add(MainItem.Divider)
                }
            }

            _itemList.value = list
        }.onError { code, message ->
            _state.value = MainState.OnError("$code / $message")
        }.onException {
            _state.value = MainState.OnError(it.message ?: "onExeption")
        }
        hideLoading()
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
