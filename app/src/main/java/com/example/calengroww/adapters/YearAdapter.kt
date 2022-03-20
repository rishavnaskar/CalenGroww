package com.example.calengroww.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.calengroww.R
import com.example.calengroww.databinding.YearRecyclerItemBinding

class YearAdapter(
    private val years: List<Int>,
    currentYearOffset: Int,
    private val context: Context,
    val onClick: (Int) -> Unit
) :
    RecyclerView.Adapter<YearAdapter.ViewHolder>() {

    var selectedItemPos = currentYearOffset
    var lastSelectedItemPos = currentYearOffset

    inner class ViewHolder(private val binding: YearRecyclerItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                selectedItemPos = adapterPosition
                onClick(selectedItemPos + years[0])

                lastSelectedItemPos = if (lastSelectedItemPos == -1) {
                    selectedItemPos
                } else {
                    notifyItemChanged(lastSelectedItemPos)
                    selectedItemPos
                }
                notifyItemChanged(selectedItemPos)
            }
        }

        fun bind(year: Int, position: Int) {
            if (position == selectedItemPos) {
                binding.root.background =
                    ContextCompat.getDrawable(context, R.drawable.item_background_selected)
            } else {
                binding.root.background =
                    ContextCompat.getDrawable(context, R.drawable.item_background_unselected)
            }
            binding.yearTextView.text = year.toString()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            YearRecyclerItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(years[position], position)
    }

    override fun getItemCount() = years.size
}