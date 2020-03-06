package com.ci.assignment.data


import com.ci.assignment.model.CategoryModel
import com.ci.assignment.model.ProductsModel
import retrofit2.Call
import retrofit2.http.*


interface API {
    @GET("5e16d5263000002a00d5616c")
    fun getCategories(): Call<CategoryModel>

    @GET("5e16d5443000004e00d5616d")
    fun getProducts(): Call<ProductsModel>
}