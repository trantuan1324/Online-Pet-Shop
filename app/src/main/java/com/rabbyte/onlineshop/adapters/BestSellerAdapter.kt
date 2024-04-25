package com.rabbyte.onlineshop.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.request.RequestOptions
import com.rabbyte.onlineshop.R
import com.rabbyte.onlineshop.activities.DetailActivity
import com.rabbyte.onlineshop.databinding.ViewholderBestSellerBinding
import com.rabbyte.onlineshop.model.ItemsModel

class BestSellerAdapter(
    private val items: List<ItemsModel>
) : RecyclerView.Adapter<BestSellerAdapter.BestSellerViewHolder>() {

    private lateinit var context: Context

    class BestSellerViewHolder(val binding: ViewholderBestSellerBinding) : ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BestSellerViewHolder {
        context = parent.context
        val binding = ViewholderBestSellerBinding.inflate(LayoutInflater.from(context), parent, false)

        return BestSellerViewHolder(binding)
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: BestSellerViewHolder, position: Int) {
        holder.binding.titleTxt.text = items[position].title
        holder.binding.priceTxt.text =
            context.getString(R.string.dollar_sign, items[position].price.toString())
        holder.binding.ratingTxt.text = items[position].rating.toString()

        val requestOptions = RequestOptions().transform(CenterCrop())
        Glide.with(holder.itemView.context)
            .load(items[position].picUrl[0])
            .apply(requestOptions)
            .into(holder.binding.picBestSeller)

        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context, DetailActivity::class.java)
            intent.putExtra("object", items[position])
            holder.itemView.context.startActivity(intent)
        }
    }

}