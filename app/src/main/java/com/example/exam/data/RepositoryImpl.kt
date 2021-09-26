package com.example.exam.data

import android.util.Log
import com.example.exam.data.retrofit.WeatherRetrofitService
import com.example.exam.data.retrofit.model.CurrentWeatherAPIResponse
import com.example.exam.model.RecyclerWeatherItem
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    val retrofitService: WeatherRetrofitService
): Repository {
    private val TAG = "RepositoryImpl"
    override suspend fun getRetrofitData(): List<RecyclerWeatherItem> {
        Log.d(TAG, "getRetrofitData: ")
        return RecyclerWeatherItem.FromForecastAPI(retrofitService.getAPIResponseForLatLong())
    }

    override suspend fun getCurrentWeatherData(): CurrentWeatherAPIResponse {
        Log.d(TAG, "getCurrentWeatherData: ")
        return retrofitService.getCurrentWeather()
    }
}