package com.example.exam.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.exam.databinding.ActivityMainBinding
import com.example.exam.model.RecyclerWeatherItem
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import android.app.ProgressDialog
import androidx.activity.ComponentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.exam.R
import com.example.exam.data.retrofit.model.CurrentWeatherAPIResponse
import com.example.exam.ui.adapters.WeatherRecyclerViewAdapter
import java.time.Instant


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private lateinit var binding: ActivityMainBinding
    val viewModel: WeatherViewModel by viewModels()
    private var progress: ProgressDialog? = null
    private lateinit var recyclerView: RecyclerView
    private lateinit var recyclerViewAdapter: WeatherRecyclerViewAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        setupUI(binding)

    }

    private fun setupUI(binding: ActivityMainBinding) {
        observeClock()
        initRecyclerView(binding)
        observeWeatherItems()
    }

    private fun observeClock() {
    }

    private fun initRecyclerView(binding: ActivityMainBinding) {
        recyclerView = binding.weatherRecyclerViewMainScreen
        recyclerViewAdapter = WeatherRecyclerViewAdapter(listOf(),this)
        recyclerView.layoutManager = LinearLayoutManager(this);
        recyclerView.adapter = recyclerViewAdapter
    }

    private fun observeWeatherItems() {
        lifecycleScope.launch {
            viewModel.weatherItemsFlow.collect {
                if(it.currentWeatherAPIResponse == null){
                    showLoadingDialog()
                } else {
                    dismissLoadingDialog()
                    loadUI(it.currentWeatherAPIResponse,it.list)
                }
            }
        }
    }

    private fun loadUI(currentWeather: CurrentWeatherAPIResponse, list: List<RecyclerWeatherItem>) {
        recyclerViewAdapter = WeatherRecyclerViewAdapter(list,this)
        recyclerView.adapter = recyclerViewAdapter
        recyclerView.invalidate()

        binding.currentTemp.text = currentWeather.main.temp.toInt().toString() + "c"
        binding.currentLocation.text = currentWeather.name
        binding.currentMaxTemp.text = currentWeather.main.tempMax.toInt().toString()
        binding.currentMinTemp.text = currentWeather.main.tempMin.toInt().toString()
        binding.tempDescription.text = currentWeather.weather[0].description
    }

    fun showLoadingDialog() {
        progress = ProgressDialog(this)!!
        progress!!.setTitle(resources.getString(R.string.loading_title))
        progress!!.setMessage(getString(R.string.loading_message))

        progress!!.show()
    }

    fun dismissLoadingDialog() {
        if (progress != null && progress!!.isShowing()) {
            progress!!.dismiss()
        }
    }
}