package com.example.exam.ui.adapters

import android.content.Context

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.exam.databinding.RecyclerViewItemBinding
import com.example.exam.model.RecyclerWeatherItem
import com.example.exam.utils.getLocalizedName
import java.time.Instant
import java.time.ZoneId

class WeatherRecyclerViewAdapter(
    val dataSet: List<RecyclerWeatherItem>,
    private val context: Context
) :
    RecyclerView.Adapter<WeatherRecyclerViewAdapter.ViewHolder>() {
    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    class ViewHolder(val binding: RecyclerViewItemBinding) : RecyclerView.ViewHolder(binding.root) {
        private  val TAG = "WeatherRecyclerViewAdap"

        fun bind(
            weatherItem: RecyclerWeatherItem,
            context: Context,
            adapter: WeatherRecyclerViewAdapter,
        ) {
            binding.root.setOnClickListener(object : View.OnClickListener {
                override fun onClick(p0: View?) {
                    adapter.dataSet.forEach {
                        it.isRecyclerViewOpened = false;
                    }
                    weatherItem.isRecyclerViewOpened = true
                    adapter.notifyDataSetChanged()
                }
            })
            val innerRecyclerViewAdapter = InnerRecyclerViewAdapter(weatherItem.hourlyData)
            binding.innerRecView.layoutManager = LinearLayoutManager(context)
            binding.innerRecView.adapter = innerRecyclerViewAdapter
            binding.recItemMaxTemp.text = weatherItem.maxTempInCelcius.toInt().toString()
            binding.recItemMinTemp.text = weatherItem.minTempInCelcius.toInt().toString()
            binding.recItemDay.text =
                getLocalizedName(
                Instant.ofEpochSecond(weatherItem.dateTimestamp.toLong())
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime().dayOfWeek.name)
            binding.recViewLayout.visibility =
                if(weatherItem.isRecyclerViewOpened) {
                    View.VISIBLE
                } else {
                    View.GONE
                }
        }


    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {

        val itemBinding = RecyclerViewItemBinding.inflate(
            LayoutInflater.from(viewGroup.context),
            viewGroup,
            false
        )
        return ViewHolder(itemBinding)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        val weatherItem: RecyclerWeatherItem = dataSet[position]
        viewHolder.bind(weatherItem, context, this)
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = dataSet.size

}
