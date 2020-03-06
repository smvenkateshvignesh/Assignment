package com.ci.assignment.model


import com.google.gson.annotations.SerializedName

data class ProductsModel(
    @SerializedName("arrayOfProducts")
    val arrayOfProducts: ArrayList<ArrayOfProduct>?
) {
    data class ArrayOfProduct(
        @SerializedName("category")
        val category: String?,
        @SerializedName("description")
        val description: String?,
        @SerializedName("imgUrl")
        val imgUrl: String?,
        @SerializedName("name")
        val name: String?,
        @SerializedName("price")
        val price: String?,
        @SerializedName("quantity")
        var quantity: Int?
    )
}