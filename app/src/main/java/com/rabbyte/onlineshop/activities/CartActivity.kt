package com.rabbyte.onlineshop.activities

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.rabbyte.onlineshop.R
import com.rabbyte.onlineshop.adapters.CartAdapter
import com.rabbyte.onlineshop.databinding.ActivityCartBinding
import com.rabbyte.onlineshop.helpers.ChangeNumberItemsListener
import com.rabbyte.onlineshop.helpers.ManagementCart

class CartActivity : BaseActivity() {

    private lateinit var binding: ActivityCartBinding
    private lateinit var managementCart: ManagementCart
    private var tax: Double = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCartBinding.inflate(layoutInflater)
        setContentView(binding.root)

        managementCart = ManagementCart(this)

        setVariable()
        initCartList()
        calculateCart()
    }

    private fun calculateCart() {
        val percentTax = 0.02
        val delivery = 15.0
        tax = Math.round(managementCart.getTotalFee() * percentTax * 100) / 100.0
        val total = Math.round((managementCart.getTotalFee() + tax + delivery) * 100) / 100
        val itemTotal = Math.round(managementCart.getTotalFee() * 100) / 100


        with(binding) {
            totalFeeTxt.text = baseContext.getString(R.string.dollar_sign, itemTotal.toString())
            taxTxt.text = baseContext.getString(R.string.dollar_sign, tax.toString())
            deliveryTxt.text = baseContext.getString(R.string.dollar_sign, delivery.toString())
            totalTxt.text = baseContext.getString(R.string.dollar_sign, total.toString())
        }
    }

    private fun initCartList() {
        binding.cartView.apply {
            layoutManager = LinearLayoutManager(this@CartActivity, LinearLayoutManager.VERTICAL, false)
            adapter = CartAdapter(managementCart.getListCart(), this@CartActivity,
                object : ChangeNumberItemsListener {
                    override fun onChanged() {
                        calculateCart()
                    }
                })
        }
    }

    private fun setVariable() {
        binding.backBtn.setOnClickListener { finish() }
    }
}