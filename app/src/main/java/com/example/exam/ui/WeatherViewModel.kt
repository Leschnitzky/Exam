package com.example.exam.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.exam.model.WeatherItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(

) : ViewModel() {

    val weatherItemsFlow : StateFlow<List<WeatherItem> >
        get() = _weatherItemsFlow

    private val _weatherItemsFlow = MutableStateFlow<List <WeatherItem> >(listOf())

    init {
        viewModelScope.launch {
            delay(2000)
            _weatherItemsFlow.value = listOf( WeatherItem(1), WeatherItem(2))

        }
    }

}