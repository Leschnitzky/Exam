package com.example.exam.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.example.exam.R
import com.example.exam.databinding.ActivityMainBinding
import com.example.exam.databinding.RecyclerViewItemBinding

class WeatherRecyclerViewAdapter(private val dataSet: Array<String>) :
    RecyclerView.Adapter<WeatherRecyclerViewAdapter.ViewHolder>() {
    lateinit var binding: RecyclerViewItemBinding
    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textView: TextView

        init {

            // Define click listener for the ViewHolder's View.
            textView =
        }
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item

        val binding: ViewDataBinding = DataBindingUtil.inflate(
            LayoutInflater.from(viewGroup.context),
            viewTypeToLayoutId[viewType] ?: 0,
            viewGroup,
            false)

        return ViewHolder(view)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        viewHolder.textView.text = dataSet[position]
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = dataSet.size

}
