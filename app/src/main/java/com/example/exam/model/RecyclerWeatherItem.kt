package com.example.exam.model

data class RecyclerWeatherItem(
    val dateTimestamp: Int,
    val maxTempInCelcius: Double,
    val minTempInCelcius: Double,
    val hourlyData: ArrayList<InnerRecyclerWeatherItem>
) {
    companion object{
        fun FromForecastAPI(forecastAPIResponse: ForecastAPIResponse) : List<RecyclerWeatherItem>{
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

        private fun updateDailyData(arrayListOfWeatherItems: ArrayList<RecyclerWeatherItem>, forecastAPIResponse: ForecastAPIResponse) {

        }

    }
}
