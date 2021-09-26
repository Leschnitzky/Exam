package com.example.exam.ui.adapters

import android.content.Context
import android.icu.lang.UCharacter.JoiningGroup.HE
import android.icu.lang.UScript.HEBREW

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.exam.databinding.RecyclerViewItemBinding
import com.example.exam.model.RecyclerWeatherItem
import java.lang.Character.UnicodeBlock.HEBREW
import java.time.Instant
import java.time.ZoneId
import java.time.format.TextStyle
import java.util.*

class WeatherRecyclerViewAdapter(
    private val dataSet: List<RecyclerWeatherItem>,
    private val context: Context
) :
    RecyclerView.Adapter<WeatherRecyclerViewAdapter.ViewHolder>() {
    var previouslyOpenedView: RecyclerViewItemBinding? = null

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    class ViewHolder(val binding: RecyclerViewItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(weatherItem: RecyclerWeatherItem, context: Context) {
            binding.root.setOnClickListener(object : View.OnClickListener {
                override fun onClick(p0: View?) {

                    if (binding.recViewLayout.visibility == View.GONE) {

                        binding.recViewLayout.visibility = View.VISIBLE
                    } else {
                        binding.recViewLayout.visibility = View.GONE
                    }
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


        }

        private fun getLocalizedName(name: String): CharSequence? {
            when(name){
                "SUNDAY" -> return "ראשון"
                "MONDAY" -> return "שני"
                "TUESDAY" -> return "שלישי"
                "WEDNESDAY" -> return "רביעי"
                "THURSDAY" -> return "חמישי"
                "FRIDAY" -> return "שישי"
                "SATURDAY" -> return "שבת"
                else -> return "N/A"
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
        previouslyOpenedView = itemBinding
        return ViewHolder(itemBinding)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        val weatherItem: RecyclerWeatherItem = dataSet[position]
        viewHolder.bind(weatherItem, context)
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = dataSet.size

}
