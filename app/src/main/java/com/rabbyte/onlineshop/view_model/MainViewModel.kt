package com.rabbyte.onlineshop.view_model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.rabbyte.onlineshop.model.CategoryModel
import com.rabbyte.onlineshop.model.ItemsModel
import com.rabbyte.onlineshop.model.SliderModel

class MainViewModel : ViewModel() {

    private val firebaseDatabase = FirebaseDatabase.getInstance()

    private val _banner = MutableLiveData<List<SliderModel>>()
    private val _category = MutableLiveData<List<CategoryModel>>()
    private val _bestSeller = MutableLiveData<List<ItemsModel>>()

    val banners: LiveData<List<SliderModel>> = _banner
    val category: LiveData<List<CategoryModel>> = _category
    val bestSeller: LiveData<List<ItemsModel>> = _bestSeller

    companion object Constant {
        const val LOAD_BANNER = "Get info add to banner"
        const val LOAD_CATEGORY = "Get info add to category"
        const val LOAD_BESTSELLER = "Get info add to best seller"
    }

    /**
     * Lắng nghe sự thay đổi của dữ liệu trong thư mục "Banner" của Firebase Realtime Database.
     * Khi dữ liệu thay đổi, hàm này lấy dữ liệu, chuyển đổi thành các đối tượng SliderModel, và cập nhật giá trị của _banner bằng danh sách các đối tượng này.
     */
    fun loadBanner() {
        // Tạo một tham chiếu đến vị trí lưu trữ dữ liệu "Banner" trong Firebase Realtime Database.
        val ref = firebaseDatabase.getReference("Banner")
        ref.addValueEventListener(object : ValueEventListener {
            // Được gọi khi dữ liệu được cập nhật. Tham số snapshot chứa ảnh của dữ liệu tại thời điểm đó.
            override fun onDataChange(snapshot: DataSnapshot) {
                val lists = mutableListOf<SliderModel>()
                for (childSnapShot in snapshot.children) {
                    // Lấy giá trị của con hiện tại và ánh xạ nó thành một đối tượng SliderModel.
                    // Nếu ánh xạ thành công (tức là dữ liệu đúng định dạng SliderModel)
                    val list = childSnapShot.getValue(SliderModel::class.java)
                    if (list != null) {
                        lists.add(list)
                    }
                    _banner.value = lists
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e(LOAD_BANNER, error.message)
            }
        })
    }

    /**
     * Lắng nghe sự thay đổi của dữ liệu trong thư mục "Category" của Firebase Realtime Database.
     * Khi dữ liệu thay đổi, hàm này lấy dữ liệu, chuyển đổi thành các đối tượng CategoryModel, và cập nhật giá trị của _category bằng danh sách các đối tượng này.
     */
    fun loadCategory() {
        val ref = firebaseDatabase.getReference("Category")
        ref.addValueEventListener(object : ValueEventListener {
            // Được gọi khi dữ liệu được cập nhật. Tham số snapshot chứa ảnh của dữ liệu tại thời điểm đó.
            override fun onDataChange(snapshot: DataSnapshot) {
                val lists = mutableListOf<CategoryModel>()

                for (childSnapShot in snapshot.children) {
                    // Lấy giá trị của con hiện tại và ánh xạ nó thành một đối tượng CategoryModel.
                    // Nếu ánh xạ thành công (tức là dữ liệu đúng định dạng CategoryModel)
                    val list = childSnapShot.getValue(CategoryModel::class.java)

                    if (list != null) {
                        lists.add(list)
                    }

                    _category.value = lists
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e(LOAD_CATEGORY, error.message)
            }
        })
    }

    fun loadBestSeller() {
        val ref = firebaseDatabase.getReference("Items")
        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val lists = mutableListOf<ItemsModel>()

                for (childSnapShot in snapshot.children) {
                    val list = childSnapShot.getValue(ItemsModel::class.java)

                    if (list != null) {
                        lists.add(list)
                    }
                }

                _bestSeller.value = lists
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e(LOAD_BESTSELLER, error.message)
            }
        })
    }
}