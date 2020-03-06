package com.ci.assignment.ui.category

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.ci.assignment.R
import com.ci.assignment.model.CategoryModel
import com.ci.assignment.ui.category.adapter.CategoryAdapter
import com.ci.assignment.ui.products.ProductsListActivity
import com.ci.assignment.utils.Network
import com.ci.assignment.utils.SpacesItemDecoration
import kotlinx.android.synthetic.main.activity_category.*

class CategoryActivity : AppCompatActivity(), CategoryView {
    companion object{
        val BUNDLE_CATEGORY_ID = "Category ID"
    }


    private var categoryPresenter: CategoryPresenter? = null
    private var categoryAdapter: CategoryAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category)
        bindPresenter()
        initViews()
    }
    private fun bindPresenter() {
        categoryPresenter = CategoryPresenter(this)
    }

    private fun initViews() {
        supportActionBar?.title = "Categories"
        recyclerViewCategory.addItemDecoration(SpacesItemDecoration(resources.getDimension(R.dimen._10dp).toInt()))
        recyclerViewCategory.layoutManager = GridLayoutManager(this, 2)
        categoryAdapter = CategoryAdapter()
        categoryAdapter?.setOnClickListener(object : CategoryAdapter.OnClickListener {
            override fun itemClicked(categoryItem: CategoryModel.ArrayOfProduct) {
                val myIntent = Intent(this@CategoryActivity, ProductsListActivity::class.java)
                myIntent.putExtra(BUNDLE_CATEGORY_ID,categoryItem.id)
                startActivity(myIntent)
            }

        })
        recyclerViewCategory.adapter = categoryAdapter
        btnRetry.setOnClickListener {
            showErrorMessage(false)
            getCategoryList()
        }

        getCategoryList()

    }

    private fun getCategoryList() {
        if (Network.isNetworkConnected()) {
            categoryPresenter?.callCategoryList()
        } else {
            showErrorMessage(true, getString(R.string.no_internet_connection))
        }
    }

    override fun startLoading() {
        progressBarLayout.visibility = View.VISIBLE
    }

    override fun finishLoading() {
        progressBarLayout.visibility = View.GONE
    }

    override fun showCategoryList(categoryModel: CategoryModel?) {
        if (categoryModel?.arrayOfProducts != null) {
            categoryAdapter?.addCategory(categoryModel.arrayOfProducts)
        }
    }

    override fun showErrorMessage(show: Boolean, message: String) {
        errorMessage.text = message
        groupErrorViews.visibility = if (show) View.VISIBLE else View.GONE
    }

}
