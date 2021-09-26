package com.example.exam.model

import com.example.exam.data.retrofit.model.CurrentWeatherAPIResponse

data class WeatherData(
    val currentWeatherAPIResponse: CurrentWeatherAPIResponse?,
    val list : List<RecyclerWeatherItem>
)
