package com.ci.assignment.ui.products

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.ci.assignment.R
import com.ci.assignment.model.ProductsModel
import com.ci.assignment.ui.category.CategoryActivity
import com.ci.assignment.ui.filter.FilterActivity
import com.ci.assignment.ui.products.adapter.ProductsAdapter
import com.ci.assignment.utils.Network
import com.ci.assignment.utils.SpacesItemDecoration
import kotlinx.android.synthetic.main.activity_category.progressBarLayout
import kotlinx.android.synthetic.main.activity_products_list.*
import kotlin.math.max


class ProductsListActivity : AppCompatActivity(), ProductsListView {


    private var productListPresenter: ProductsListPresenter? = null
    private var productsAdapter: ProductsAdapter? = null
    private var categoryId = ""
    private val REQUEST_CODE_FILTER = 1001
    private var productsList: ArrayList<ProductsModel.ArrayOfProduct> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_products_list)
        getIntentData()
        bindPresenter()
        initViews()
    }

    private fun bindPresenter() {
        productListPresenter = ProductsListPresenter(this)
    }

    private fun getIntentData() {
        if (intent.hasExtra(CategoryActivity.BUNDLE_CATEGORY_ID)) {
            categoryId = intent.getStringExtra(CategoryActivity.BUNDLE_CATEGORY_ID)
        }
    }

    private fun initViews() {
        supportActionBar?.title = "Products"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        recyclerViewProducts.addItemDecoration(SpacesItemDecoration(resources.getDimension(R.dimen._10dp).toInt()))
        recyclerViewProducts.layoutManager = GridLayoutManager(this, 2)
        productsAdapter = ProductsAdapter()
        recyclerViewProducts.adapter = productsAdapter
        btnProductRetry.setOnClickListener {
            showErrorMessage(false)
            getProductsList()
        }
        getProductsList()

    }

    private fun getProductsList() {
        if (Network.isNetworkConnected()) {
            productListPresenter?.callProductsList()
        } else {
            showErrorMessage(true, getString(R.string.no_internet_connection), true)
        }
    }

    override fun startLoading() {
        progressBarLayout.visibility = View.VISIBLE
    }

    override fun finishLoading() {
        progressBarLayout.visibility = View.GONE
    }

    override fun showProducts(products: ProductsModel?) {
        //Removed other category products
        if (products?.arrayOfProducts != null) {
            val iterator = products.arrayOfProducts.iterator()
            while (iterator.hasNext()) {
                val item = iterator.next()
                if (!item.category.equals(categoryId)) {
                    iterator.remove()
                }
            }
            productsList.addAll(products.arrayOfProducts)
            showFilteredProducts()
        }

        //Showing all products without comparision with category id
//        if (products?.arrayOfProducts != null) {
//            productsList.addAll(products.arrayOfProducts)
//            showFilteredProducts()
//        }
    }

    private fun showFilteredProducts() {
        if (productsList.size > 0) {
            val filteredList = ArrayList<ProductsModel.ArrayOfProduct>()
            val minimumStock = if (FilterActivity.inStock) 1 else 0
            var minVale = 0
            var maxValue = -1
            for (values in FilterActivity.priceModels) {
                if (values.isChecked) {
                    minVale = values.min
                    maxValue = values.max
                }
            }

            for (item in productsList) {
                if (item.quantity ?: 0 >= minimumStock && checkValueByFilter(
                        (item.price ?: "0").toDouble(), minVale, maxValue
                    )
                ) {
                    filteredList.add(item)
                }
            }
            productsAdapter?.addCategory(filteredList)
            if (filteredList.size <= 0) {
                showErrorMessage(true, "No Products", false)
            } else {
                showErrorMessage(false)
            }
        } else {
            showErrorMessage(true, "No Products", false)
        }
    }

    private fun checkValueByFilter(prize: Double, minVale: Int, maxValue: Int): Boolean {
        return if (maxValue == -1) {
            prize >= minVale
        } else minVale <= prize && prize <= maxValue
    }

    override fun showErrorMessage(show: Boolean, message: String, showRetry: Boolean) {
        errorProductMessage.text = message
        errorProductMessage.visibility = if (show) View.VISIBLE else View.GONE
        btnProductRetry.visibility = if (showRetry) View.VISIBLE else View.GONE
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
            }
            R.id.action_filter -> {
                startActivityForResult(
                    Intent(
                        this@ProductsListActivity,
                        FilterActivity::class.java
                    ), REQUEST_CODE_FILTER
                )
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.product_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            REQUEST_CODE_FILTER -> {
                if (resultCode == Activity.RESULT_OK) {
                    showFilteredProducts()
                }
            }
        }
    }

    override fun onDestroy() {
        FilterActivity.clearSharedPreference()
        super.onDestroy()
    }
}
