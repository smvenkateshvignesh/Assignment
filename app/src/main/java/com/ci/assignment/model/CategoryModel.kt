package com.ci.assignment.model


import com.google.gson.annotations.SerializedName

data class CategoryModel(
    @SerializedName("arrayOfProducts")
    val arrayOfProducts: ArrayList<ArrayOfProduct>
) {
    data class ArrayOfProduct(
        @SerializedName("id")
        val id: String?,
        @SerializedName("imgUrl")
        val imgUrl: String?,
        @SerializedName("name")
        val name: String?
    )
}