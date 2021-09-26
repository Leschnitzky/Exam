package com.example.exam.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.exam.databinding.InnerRecyclerViewItemBinding
import com.example.exam.model.InnerRecyclerWeatherItem

class InnerRecyclerViewAdapter (private val dataSet: List<InnerRecyclerWeatherItem>) :
    RecyclerView.Adapter<InnerRecyclerViewAdapter.ViewHolder>()  {

    class ViewHolder(val binding: InnerRecyclerViewItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(weatherItem: InnerRecyclerWeatherItem){

        }
    }
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val itemBinding = InnerRecyclerViewItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val weatherItem: InnerRecyclerWeatherItem = dataSet[position]
        holder.bind(weatherItem)
    }

    override fun getItemCount(): Int {
        return dataSet.count()
    }
}