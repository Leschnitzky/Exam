package com.example.exam.model

import android.util.Log
import com.example.exam.utils.DAY
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

data class RecyclerWeatherItem(
    val dateTimestamp: Int,
    val maxTempInCelcius: Double,
    val minTempInCelcius: Double,
    val hourlyData: ArrayList<InnerRecyclerWeatherItem>
) {
    companion object {
        private const val TAG = "RecyclerWeatherItem"
        fun FromForecastAPI(forecastAPIResponse: ForecastAPIResponse): List<RecyclerWeatherItem> {
            val arrayListOfWeatherItems: ArrayList<RecyclerWeatherItem> = arrayListOf()
            forecastAPIResponse.daily.forEach {
                val item = RecyclerWeatherItem(
                    it.dt,
                    it.temp.max,
                    it.temp.min,
                    arrayListOf()
                )
                arrayListOfWeatherItems.add(item)
            }
            updateDailyData(arrayListOfWeatherItems, forecastAPIResponse)
            return arrayListOfWeatherItems
        }

        private fun updateDailyData(
            arrayListOfWeatherItems: ArrayList<RecyclerWeatherItem>,
            forecastAPIResponse: ForecastAPIResponse
        ) {
            arrayListOfWeatherItems.sortBy {
                it.dateTimestamp
            }
            forecastAPIResponse.hourly.forEach { dailyItem ->
                Log.d(TAG, "updateDailyData: Time: ${                          
                    Instant.ofEpochSecond(dailyItem.dt.toLong())
                    .atZone(ZoneId.systemDefault()).format(
                            DateTimeFormatter.ofPattern("kk:mm")
                        )}, Temp: ${dailyItem.temp.toInt()}")
                arrayListOfWeatherItems
                    .filter { Instant.ofEpochSecond(dailyItem.dt.toLong())
                        .atZone(ZoneId.systemDefault()).dayOfYear == Instant.ofEpochSecond(it.dateTimestamp.toLong())
                        .atZone(ZoneId.systemDefault()).dayOfYear }
                    .forEach {
                        Log.d(TAG, "updateDailyData: Adding to ${InnerRecyclerWeatherItem(
                            Instant.ofEpochSecond(dailyItem.dt.toLong())
                                .atZone(ZoneId.systemDefault()).format(
                                    DateTimeFormatter.ofPattern("kk:mm")
                                )
                            ,
                            dailyItem.temp
                        )} ")

                    it.hourlyData.add(
                        InnerRecyclerWeatherItem(
                            Instant.ofEpochSecond(dailyItem.dt.toLong())
                                .atZone(ZoneId.systemDefault()).format(
                                    DateTimeFormatter.ofPattern("kk:mm")
                                )
                            ,
                            dailyItem.temp
                        )
                    )
                }
            }
        }

    }
}
