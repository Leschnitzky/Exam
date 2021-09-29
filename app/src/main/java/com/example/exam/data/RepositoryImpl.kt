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
    override suspend fun getRetrofitData(lat: String, lon: String): List<RecyclerWeatherItem> {
        val array =  RecyclerWeatherItem.FromForecastAPI(retrofitService.getAPIResponseForLatLong(lat = lat, lon = lon))
        Log.d(TAG, "getRetrofitData: $array")
        return array
    }

    override suspend fun getCurrentWeatherData(
        cityName: String
    ): CurrentWeatherAPIResponse {
        return retrofitService.getCurrentWeather(cityName = cityName)
    }

}