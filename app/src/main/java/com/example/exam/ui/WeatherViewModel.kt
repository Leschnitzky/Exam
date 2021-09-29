package com.example.exam.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.exam.data.Repository
import com.example.exam.model.WeatherData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {
    private val TAG = "WeatherViewModel"
    val weatherItemsFlow: StateFlow<WeatherData>
        get() = _weatherItemsFlow
    private val _weatherItemsFlow = MutableStateFlow(WeatherData(null, listOf()))

    suspend fun getCurrentDataForLatLong(lat : String, lon: String, cityName: String){
        Log.d(TAG, "getCurrentDataForLatLong: $lat , $lon , $cityName")
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                repository.getCurrentWeatherData(cityName).also { currentData ->
                    repository.getRetrofitData(lat,lon).also {
                        withContext(Dispatchers.Main) {
                            _weatherItemsFlow.value = WeatherData(currentData, it)
                        }
                    }
                }
            }
        }
    }
}
