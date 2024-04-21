package com.rabbyte.onlineshop.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterInside
import com.bumptech.glide.request.RequestOptions
import com.rabbyte.onlineshop.R
import com.rabbyte.onlineshop.model.SliderModel

class SliderAdapter(
    private var sliderItems: List<SliderModel>,
    private val viewPager2: ViewPager2
) : RecyclerView.Adapter<SliderAdapter.SliderViewModel>() {

    private lateinit var context: Context

    class SliderViewModel(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imageView: ImageView = itemView.findViewById(R.id.imageSlide)

        fun setImage(sliderItems: SliderModel, context: Context) {
            // cấu hình thư viện Glide
            // đảm bảo ảnh được hiển thị chính giữa imageView bất kể kích thước ảnh và kích thước của imageView.
            val requestOptions = RequestOptions().transform(CenterInside())

            Glide.with(context)
                .load(sliderItems.url)
                .apply(requestOptions)
                .into(imageView)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SliderViewModel {
        context = parent.context
        val view = LayoutInflater.from(parent.context).inflate(R.layout.slider_image_container, parent, false)

        return SliderViewModel(view)
    }

    override fun getItemCount() = sliderItems.size

    override fun onBindViewHolder(holder: SliderViewModel, position: Int) {
        holder.setImage(sliderItems[position], context)

        if (position == sliderItems.lastIndex - 1) {
            viewPager2.post {
                notifyDataSetChanged()
            }
        }
    }
}