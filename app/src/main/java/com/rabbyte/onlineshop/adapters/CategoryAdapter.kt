package com.rabbyte.onlineshop.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.rabbyte.onlineshop.databinding.ViewholderCategoryBinding
import com.rabbyte.onlineshop.model.CategoryModel

class CategoryAdapter(val items: MutableList<CategoryModel>) : RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {

    private lateinit var context: Context

    inner class CategoryViewHolder(val binding: ViewholderCategoryBinding) : ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        context = parent.context
        val binding = ViewholderCategoryBinding.inflate(LayoutInflater.from(context), parent, false)
        return CategoryViewHolder(binding)
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val item = items[position]
        holder.binding.titleCat.text = item.title

        Glide.with(holder.itemView.context)
            .load(item.picUrl)
            .into(holder.binding.picCat)
    }

}