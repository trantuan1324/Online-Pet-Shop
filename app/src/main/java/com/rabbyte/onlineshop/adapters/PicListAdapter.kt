package com.rabbyte.onlineshop.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.rabbyte.onlineshop.R
import com.rabbyte.onlineshop.databinding.ViewholderPicListBinding

class PicListAdapter(
    private val items: List<String>,
    var picMain: ImageView
): RecyclerView.Adapter<PicListAdapter.PicListViewHolder>() {

    private var selectedPos = -1
    private var lastSelectedPos = -1
    private lateinit var context: Context

    inner class PicListViewHolder(val binding: ViewholderPicListBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PicListViewHolder {
        context = parent.context
        val binding = ViewholderPicListBinding.inflate(LayoutInflater.from(context), parent, false)
        return PicListViewHolder(binding)
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: PicListViewHolder, position: Int) {

        Glide.with(holder.itemView.context)
            .load(items[position])
            .into(holder.binding.picList)

        // Thiết lập sự kiện click cho mỗi item. Khi click vào một item, vị trí item được click (position) sẽ được lưu vào biến selectedPos.
        // Đồng thời, vị trí của item được chọn trước đó (lastSelectedPos) cũng được lưu lại.
        holder.binding.root.setOnClickListener {
            lastSelectedPos = selectedPos
            selectedPos = position
            // adapter gọi notifyItemChanged để thông báo cho RecyclerView cập nhật lại giao diện
            // cho cả item cũ được chọn (lastSelectedPos) và item mới được chọn (selectedPos).
            notifyItemChanged(lastSelectedPos)
            notifyItemChanged(selectedPos)

            Glide.with(holder.itemView.context)
                .load(items[position])
                .into(picMain)
        }

        // Kiểm tra vị trí item hiện tại (position) có trùng với vị trí item đang được chọn (selectedPos) không.
        if (selectedPos == position)
            holder.binding.picLayout.setBackgroundResource(R.drawable.grey_bg_selected)
        else
            holder.binding.picLayout.setBackgroundResource(R.drawable.grey_bg)
    }

}