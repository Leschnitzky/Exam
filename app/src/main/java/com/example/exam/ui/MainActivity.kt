package com.example.exam.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.exam.databinding.ActivityMainBinding
import com.example.exam.model.WeatherItem
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import android.app.ProgressDialog
import androidx.activity.ComponentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.exam.R


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
        initRecyclerView(binding)
        observeWeatherItems()
    }

    private fun initRecyclerView(binding: ActivityMainBinding) {
        recyclerView = binding.weatherRecyclerViewMainScreen
        recyclerViewAdapter = WeatherRecyclerViewAdapter(listOf())
        recyclerView.layoutManager = LinearLayoutManager(this);
        recyclerView.adapter = recyclerViewAdapter
    }

    private fun observeWeatherItems() {
        lifecycleScope.launch {
            viewModel.weatherItemsFlow.collect {
                if(it.isEmpty()){
                    showLoadingDialog()
                } else {
                    dismissLoadingDialog()
                    loadUI(it)
                }
            }
        }
    }

    private fun loadUI(it: List<WeatherItem>) {
        recyclerViewAdapter = WeatherRecyclerViewAdapter(it)
        recyclerView.adapter = recyclerViewAdapter
        recyclerView.invalidate()
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