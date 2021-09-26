package com.example.exam.data

import com.example.exam.data.retrofit.model.CurrentWeatherAPIResponse
import com.example.exam.model.RecyclerWeatherItem

interface Repository {
    suspend fun getRetrofitData() : List<RecyclerWeatherItem>

    suspend fun getCurrentWeatherData() : CurrentWeatherAPIResponse
}