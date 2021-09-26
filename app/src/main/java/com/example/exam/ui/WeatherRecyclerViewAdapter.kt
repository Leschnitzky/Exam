package com.example.exam.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.exam.databinding.RecyclerViewItemBinding
import com.example.exam.model.WeatherItem

class WeatherRecyclerViewAdapter(private val dataSet: List<WeatherItem>) :
    RecyclerView.Adapter<WeatherRecyclerViewAdapter.ViewHolder>() {
    lateinit var binding: RecyclerViewItemBinding

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    class ViewHolder(binding: RecyclerViewItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(weatherItem: WeatherItem){
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
        val weatherItem: WeatherItem = dataSet[position]
        viewHolder.bind(weatherItem)
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = dataSet.size

}
