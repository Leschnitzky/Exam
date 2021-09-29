package com.example.exam.data.retrofit

import com.example.exam.data.retrofit.model.CurrentWeatherAPIResponse
import com.example.exam.model.ForecastAPIResponse
import com.example.exam.utils.apiKey
import com.example.exam.utils.hodHaSharonCityID
import com.example.exam.utils.hodHaSharonLat
import com.example.exam.utils.hodHaSharonLon
import retrofit2.http.GET
import retrofit2.http.Query


interface WeatherRetrofitService {

    @GET("onecall")
    suspend fun getAPIResponseForLatLong(
        @Query("lat") lat: String = hodHaSharonLat,
        @Query("lon") lon: String = hodHaSharonLon,
        @Query("appid") appId: String = apiKey,
        @Query("units") units: String = "metric"
    ): ForecastAPIResponse


    @GET("weather")
    suspend fun getCurrentWeather(
        @Query("q") cityName: String = hodHaSharonCityID,
        @Query("appid") appId: String = apiKey,
        @Query("units") units: String = "metric",
        @Query("lang") lang: String = "he"
    ): CurrentWeatherAPIResponse
}