package com.ci.assignment.ui.category

import com.ci.assignment.model.CategoryModel
import com.ci.videoplayertask.data.BaseRetrofit
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CategoryPresenter(private val categoryView: CategoryView) {
    fun callCategoryList() {
        categoryView.startLoading()
        BaseRetrofit.apis?.getCategories()?.enqueue(object : Callback<CategoryModel> {
            override fun onFailure(call: Call<CategoryModel>, t: Throwable) {
                categoryView.finishLoading()
                categoryView.showErrorMessage(true,t.message.toString())
            }

            override fun onResponse(
                call: Call<CategoryModel>,
                response: Response<CategoryModel>
            ) {
                if(response.isSuccessful){
                    categoryView.showCategoryList(response.body())
                    categoryView.showErrorMessage(false)
                    categoryView.finishLoading()
                }else{
                    categoryView.showErrorMessage(true,"Response Failed")

                }


            }
        })
    }
}