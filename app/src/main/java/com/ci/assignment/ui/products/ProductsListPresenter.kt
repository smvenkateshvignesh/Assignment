package com.ci.assignment.ui.products

import com.ci.assignment.model.ProductsModel
import com.ci.videoplayertask.data.BaseRetrofit
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProductsListPresenter(private val productsListView: ProductsListView) {
    fun callProductsList() {
        productsListView.startLoading()
        BaseRetrofit.apis?.getProducts()?.enqueue(object : Callback<ProductsModel> {
            override fun onFailure(call: Call<ProductsModel>, t: Throwable) {
                productsListView.finishLoading()
            }

            override fun onResponse(
                call: Call<ProductsModel>,
                response: Response<ProductsModel>
            ) {
                if (response.isSuccessful) {
                    productsListView.showErrorMessage(false)
                    productsListView.showProducts(response.body())
                    productsListView.finishLoading()
                } else {
                    productsListView.showErrorMessage(true, "Response Failed",true)
                }
            }
        })
    }
}