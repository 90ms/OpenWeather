package com.a90ms.domain.usecase

import com.a90ms.domain.base.CommonDtoUseCase
import com.a90ms.domain.data.dto.ListDto
import com.a90ms.domain.data.entity.city.City
import com.a90ms.domain.di.IoDispatcher
import com.a90ms.domain.repository.ApiRepository
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class GetForecastListUseCase @Inject constructor(
    private val apiRepository: ApiRepository,
    @IoDispatcher dispatcher: CoroutineDispatcher
) : CommonDtoUseCase<City, List<ListDto>>(dispatcher) {

    override suspend fun execute(parameters: City) =
        apiRepository.getForecast(parameters.coord.lat, parameters.coord.lon)
}
