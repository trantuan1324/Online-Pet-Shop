package com.rabbyte.onlineshop.activities

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.request.RequestOptions
import com.rabbyte.onlineshop.R
import com.rabbyte.onlineshop.adapters.PicListAdapter
import com.rabbyte.onlineshop.adapters.SizeListAdapter
import com.rabbyte.onlineshop.databinding.ActivityDetailBinding
import com.rabbyte.onlineshop.helpers.ManagementCart
import com.rabbyte.onlineshop.model.ItemsModel

class DetailActivity : BaseActivity() {

    private lateinit var binding: ActivityDetailBinding
    private lateinit var item: ItemsModel
    private var numberOrder = 1
    private lateinit var managementCart: ManagementCart

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        managementCart = ManagementCart(this)

        getBundle()
        initList()
    }

    private fun initList() {
        val sizeList = ArrayList<String>()
        for (size in item.size) {
            sizeList.add(size)
        }

        binding.sizeList.adapter = SizeListAdapter(sizeList)
        binding.sizeList.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        val colorList = ArrayList<String>()
        for (imageUrl in item.picUrl) {
            colorList.add(imageUrl)
        }

        Glide.with(this)
            .load(colorList[0])
            .into(binding.picMain)

        binding.picList.adapter = PicListAdapter(colorList, binding.picMain)
        binding.picList.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
    }

    private fun getBundle() {
        item = intent.getParcelableExtra("object")!!

        with(binding) {
            titleTxt.text = item.title
            descriptionTxt.text = item.description
            priceTxt.text = baseContext.getString(R.string.dollar_sign, item.price.toString())
            ratingTxt.text = getString(R.string.rating, item.rating.toString())
            sellerNameTxt.text = item.sellerName

            addToCartBtn.setOnClickListener {
                item.numberInCart = numberOrder
                managementCart.insertItems(item)
            }

            backBtn.setOnClickListener { finish() }
            binding.cartBtn.setOnClickListener {

            }

            Glide.with(this@DetailActivity)
                .load(item.sellerPic)
                .apply(RequestOptions().transform(CenterCrop()))
                .into(binding.picSeller)

            msgToSellerBtn.setOnClickListener {
                val sendIntent = Intent(Intent.ACTION_VIEW)
                sendIntent.setData(Uri.parse("sms:" + item.sellerTell))
                sendIntent.putExtra("sms_body", "type your message")
                startActivity(sendIntent)
            }

            binding.calToSellerBtn.setOnClickListener {
                val phone = item.sellerTell.toString()
                val intent = Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phone, null))
                startActivity(intent)
            }
        }

    }
}