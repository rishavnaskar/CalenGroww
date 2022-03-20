package com.example.calengroww.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.calengroww.R
import com.example.calengroww.databinding.CalenderRecyclerItemBinding

class CalenAdapter(
    private val days: List<String>,
    currentNonShiftedDay: Int,
    private val context: Context,
    val onClick: (Int) -> Unit
) : RecyclerView.Adapter<CalenAdapter.ViewHolder>() {

    var selectedItemPos = currentNonShiftedDay
    var lastSelectedItemPos = currentNonShiftedDay

    inner class ViewHolder(private val binding: CalenderRecyclerItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                if (days[adapterPosition] != "") {
                    selectedItemPos = adapterPosition
                    onClick(selectedItemPos)

                    lastSelectedItemPos = if (lastSelectedItemPos == -1) {
                        selectedItemPos
                    } else {
                        notifyItemChanged(lastSelectedItemPos)
                        selectedItemPos
                    }
                    notifyItemChanged(selectedItemPos)
                }
            }
        }

        fun bind(day: String, position: Int) {
            if (position == selectedItemPos) {
                binding.dateTextView.background =
                    ContextCompat.getDrawable(context, R.drawable.item_background_selected)
            } else {
                binding.dateTextView.background =
                    ContextCompat.getDrawable(context, R.drawable.item_background_unselected)
            }
            binding.dateTextView.text = day
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            CalenderRecyclerItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(days[position], position)
    }

    override fun getItemCount() = days.size
}