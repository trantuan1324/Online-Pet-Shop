package com.rabbyte.onlineshop.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import com.rabbyte.onlineshop.adapters.BestSellerAdapter
import com.rabbyte.onlineshop.adapters.CategoryAdapter
import com.rabbyte.onlineshop.adapters.SliderAdapter
import com.rabbyte.onlineshop.databinding.ActivityMainBinding
import com.rabbyte.onlineshop.model.SliderModel
import com.rabbyte.onlineshop.view_model.MainViewModel

class MainActivity : BaseActivity() {

    private val viewModel = MainViewModel()

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initBanners()
        initCategory()
        initBestSeller()
        setUpBottomNav()
    }

    private fun setUpBottomNav() {
        binding.cartBtn.setOnClickListener { startActivity(Intent(this, CartActivity::class.java)) }
    }

    private fun initBestSeller() {
        binding.progressBarBestSeller.visibility = View.VISIBLE
        viewModel.bestSeller.observe(this) {
            binding.viewBestSeller.apply {
                layoutManager = GridLayoutManager(this@MainActivity, 2)
                adapter = BestSellerAdapter(it)
            }
            binding.progressBarBestSeller.visibility = View.GONE
        }
        viewModel.loadBestSeller()
    }

    private fun initCategory() {
        binding.progressBarCategory.visibility = View.VISIBLE
        viewModel.category.observe(this) {
            binding.viewCategory.layoutManager = LinearLayoutManager(this@MainActivity, LinearLayoutManager.HORIZONTAL, false)
            binding.viewCategory.adapter = CategoryAdapter(it)
            binding.progressBarCategory.visibility = View.GONE
        }
        viewModel.loadCategory()
    }

    private fun initBanners() {
        binding.progressBarBanner.visibility = View.VISIBLE
        viewModel.banners.observe(this) {
            setBanners(it)
            binding.progressBarBanner.visibility = View.GONE
        }
        viewModel.loadBanner()
    }

    /**
     * Hàm Banners thiết lập viewPagerSlider để hiển thị danh sách ảnh dạng Slider.
     * Nó cấu hình adapter, thuộc tính hiển thị, hiệu ứng chuyển trang, và thanh chấm thể hiện số trang.
     */
    private fun setBanners(image: List<SliderModel>) {
        binding.viewPagerSlider.apply {
            adapter = SliderAdapter(image, binding.viewPagerSlider)
            // Cho phép nội dung của các trang Slider hiển thị vùng đệm (padding) của viewPagerSlider.
            clipToPadding = false
            // Tắt tính năng cắt nội dung của các trang con (child) vượt quá kích thước của viewPagerSlider.
            clipChildren = false
            // Thiết lập số trang tối đa được giữ trong bộ nhớ cache. 3 trang gần trang đang hiển thị sẽ được giữ để tránh tải lại ảnh khi cuộn trang.
            offscreenPageLimit = 3
            // Tắt hiệu ứng kéo dãn (overscroll) trên trang đầu tiên của viewPagerSlider.
            getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER

            // kết hợp nhiều hiệu ứng chuyển trang.
            val compositePageTransformer = CompositePageTransformer().apply {
                //  thêm hiệu ứng MarginPageTransformer với khoảng lề 40dp cho các trang.
                addTransformer(MarginPageTransformer(40))
            }

            // Thiết lập bộ chuyển trang compositePageTransformer cho viewPagerSlider.
            setPageTransformer(compositePageTransformer)

            if (image.size > 1) {
                // Hiển thị thanh chấm thể hiện số trang (dotIndicator) của viewPagerSlider.
                binding.dotIndicator.visibility = View.VISIBLE
                // Liên kết thanh chấm dotIndicator với viewPagerSlider để cập nhật trạng thái theo vị trí trang hiện tại.
                binding.dotIndicator.attachTo(this)
            }
        }
    }
}