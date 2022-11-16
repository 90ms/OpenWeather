package com.a90ms.openweather

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.a90ms.domain.data.dto.ListDto
import com.a90ms.domain.repository.ApiRepository
import com.a90ms.openweather.data.cityList
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var apiRepository: ApiRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        getForeCast()
    }

    private fun getForeCast() {
        lifecycleScope.launch {
            val seoulDeferred =
                async { apiRepository.getForecast(cityList[0].coord.lat, cityList[0].coord.lon) }
            val londonDeferred =
                async { apiRepository.getForecast(cityList[1].coord.lat, cityList[1].coord.lon) }
            val chicagoDeferred =
                async { apiRepository.getForecast(cityList[2].coord.lat, cityList[2].coord.lon) }


            val manufactureSeoul = seoulDeferred.await().manufactureList()
            val manufactureLondon = londonDeferred.await().manufactureList()
            val manufactureChicago = chicagoDeferred.await().manufactureList()

            Timber.d("manufactureSeoul = $manufactureSeoul")
            Timber.d("manufactureLondon = $manufactureLondon")
            Timber.d("manufactureChicago = $manufactureChicago")
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