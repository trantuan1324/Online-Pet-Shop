package com.rabbyte.onlineshop.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.request.RequestOptions
import com.rabbyte.onlineshop.R
import com.rabbyte.onlineshop.databinding.ViewholderCartBinding
import com.rabbyte.onlineshop.helpers.ChangeNumberItemsListener
import com.rabbyte.onlineshop.helpers.ManagementCart
import com.rabbyte.onlineshop.model.ItemsModel

class CartAdapter(
    private val items: ArrayList<ItemsModel>,
    val context: Context,
    var changeNumberItemsListener: ChangeNumberItemsListener
) : RecyclerView.Adapter<CartAdapter.CardViewHolder>() {

    private val managementCart = ManagementCart(context)

    inner class CardViewHolder(val binding: ViewholderCartBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        val binding = ViewholderCartBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CardViewHolder(binding)
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        val item = items[position]

        holder.binding.apply {
            TitleTxt.text = item.title
            feeEachItem.text = context.getString(R.string.dollar_sign, item.price.toString())
            totalEachItem.text = context.getString(R.string.dollar_sign, item.getTotalPayment().toString())
            numberItemTxt.text = item.numberInCart.toString()

            Glide.with(holder.itemView.context)
                .load(item.picUrl[0])
                .apply(RequestOptions().transform(CenterCrop()))
                .into(holder.binding.picCart)

            holder.binding.plusCartBtn.setOnClickListener {
                managementCart.plusItem(items, position, object : ChangeNumberItemsListener {
                    override fun onChanged() {
                        notifyDataSetChanged()
                        changeNumberItemsListener.onChanged()
                    }
                })
            }

            holder.binding.minusCartBtn.setOnClickListener {
                managementCart.minusItem(items, position, object : ChangeNumberItemsListener {
                    override fun onChanged() {
                        notifyDataSetChanged()
                        changeNumberItemsListener.onChanged()
                    }
                })
            }
        }
    }
}