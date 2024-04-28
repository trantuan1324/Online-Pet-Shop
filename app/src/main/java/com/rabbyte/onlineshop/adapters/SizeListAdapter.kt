package com.rabbyte.onlineshop.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.rabbyte.onlineshop.R
import com.rabbyte.onlineshop.databinding.ViewholderSizeBinding

class SizeListAdapter(
    private val items: List<String>
) : RecyclerView.Adapter<SizeListAdapter.SizeListViewHolder>() {

    private var selectedPos = -1
    private var lastSelectedPos = -1
    private lateinit var context: Context

    inner class SizeListViewHolder(val binding: ViewholderSizeBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SizeListViewHolder {
        context = parent.context
        val binding = ViewholderSizeBinding.inflate(LayoutInflater.from(context), parent, false)
        return SizeListViewHolder(binding)
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: SizeListViewHolder, position: Int) {
        holder.binding.sizeTxt.text = items[position]

        holder.binding.root.setOnClickListener {
            lastSelectedPos = selectedPos
            selectedPos = position
            notifyItemChanged(lastSelectedPos)
            notifyItemChanged(selectedPos)
        }

        if (selectedPos == position) {
            holder.binding.sizeLayout.setBackgroundResource(R.drawable.green_bg3)
            holder.binding.sizeTxt.setTextColor(context.resources.getColor(R.color.white))
        } else {
            holder.binding.sizeLayout.setBackgroundResource(R.drawable.grey_bg)
            holder.binding.sizeTxt.setTextColor(context.resources.getColor(R.color.black))
        }
    }
}