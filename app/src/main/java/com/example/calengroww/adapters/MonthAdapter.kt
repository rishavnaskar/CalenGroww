package com.example.calengroww.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.calengroww.R
import com.example.calengroww.databinding.MonthRecyclerItemBinding

class MonthAdapter(
    private val months: List<String>,
    currentMonth: Int,
    private val context: Context,
    val onClick: (Int) -> Unit
) : RecyclerView.Adapter<MonthAdapter.ViewHolder>() {

    var selectedItemPos = currentMonth
    var lastSelectedItemPost = currentMonth

    inner class ViewHolder(private val binding: MonthRecyclerItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                selectedItemPos = adapterPosition
                onClick(selectedItemPos)

                if (lastSelectedItemPost == -1) {
                    lastSelectedItemPost = selectedItemPos
                } else {
                    notifyItemChanged(lastSelectedItemPost)
                    lastSelectedItemPost = selectedItemPos
                }
                notifyItemChanged(selectedItemPos)
            }
        }

        fun bind(month: String, position: Int) {
            if (position == selectedItemPos) {
                binding.root.background =
                    ContextCompat.getDrawable(context, R.drawable.item_background_selected)
            } else {
                binding.root.background =
                    ContextCompat.getDrawable(context, R.drawable.item_background_unselected)
            }
            binding.monthTextView.text = month
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = MonthRecyclerItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(months[position], position)
    }

    override fun getItemCount() = months.size
}