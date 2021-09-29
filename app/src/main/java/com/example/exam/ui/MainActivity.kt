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
import android.widget.Toast
import com.example.exam.utils.getLocalizedName
import java.lang.StringBuilder
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.concurrent.Executors
import java.util.concurrent.ScheduledExecutorService
import java.util.concurrent.ThreadPoolExecutor
import java.util.concurrent.TimeUnit


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private lateinit var binding: ActivityMainBinding
    val viewModel: WeatherViewModel by viewModels()
    private var progress: ProgressDialog? = null
    private lateinit var recyclerView: RecyclerView
    private lateinit var recyclerViewAdapter: WeatherRecyclerViewAdapter
    private lateinit var scheduleTaskExecutor : ScheduledExecutorService


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
        // Since we just need it to run when application is live, we can destroy the TimerTask on OnDestroy no need for alarm managers and such
        scheduleTaskExecutor = Executors.newScheduledThreadPool(5)
        scheduleTaskExecutor.scheduleAtFixedRate(Runnable {
            runOnUiThread {
                val currentTime = System.currentTimeMillis()
                val date = Instant.ofEpochSecond(currentTime / 1000)
                    .atZone(ZoneId.systemDefault())
                binding.currentTime.text =
                    StringBuilder()
                        .append(date.format(DateTimeFormatter.ofPattern("kk:mm")))
                        .append(", ")
                        .append(getLocalizedName(date.dayOfWeek.name))
                        .toString()

            }
        }, 0, 1, TimeUnit.MINUTES) // or .MINUTES, .HOURS etc.


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

    override fun onDestroy() {
        scheduleTaskExecutor.shutdown()
        super.onDestroy()
    }
}