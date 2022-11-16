package com.a90ms.openweather

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
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

            val seoulList = seoulDeferred.await()
            val londonList = londonDeferred.await()
            val chicagoList = chicagoDeferred.await()


            Timber.d("a=$seoulList")
            Timber.d("b=$londonList")
            Timber.d("c=$chicagoList")
        }
    }
}