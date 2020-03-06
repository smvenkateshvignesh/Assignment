package com.ci.assignment.ui.products

import com.ci.assignment.model.ProductsModel

interface ProductsListView {
    fun startLoading()
    fun finishLoading()
    fun showErrorMessage(show: Boolean, message: String = "",showRetry:Boolean= false)
    fun showProducts(products: ProductsModel?)
}