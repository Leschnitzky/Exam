package com.example.exam.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.exam.data.Repository
import com.example.exam.data.retrofit.model.CurrentWeatherAPIResponse
import com.example.exam.model.InnerRecyclerWeatherItem
import com.example.exam.model.RecyclerWeatherItem
import com.example.exam.model.WeatherData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import okhttp3.Dispatcher
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    repository: Repository
) : ViewModel() {

    val weatherItemsFlow: StateFlow<WeatherData>
        get() = _weatherItemsFlow
    private val _weatherItemsFlow = MutableStateFlow(WeatherData(null, listOf()))


    init {

        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                    repository.getCurrentWeatherData().also {
                        withContext(Dispatchers.Main){
                            _weatherItemsFlow.value = WeatherData(it,repository.getRetrofitData())
                        }
                    }

            }
        }
    }

}