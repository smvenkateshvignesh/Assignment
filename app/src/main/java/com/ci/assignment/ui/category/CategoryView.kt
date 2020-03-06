package com.ci.assignment.ui.category

import com.ci.assignment.model.CategoryModel

interface CategoryView {

    fun startLoading()
    fun finishLoading()
    fun showErrorMessage(show: Boolean, message: String = "")
    fun showCategoryList(categoryModel: CategoryModel?)
}