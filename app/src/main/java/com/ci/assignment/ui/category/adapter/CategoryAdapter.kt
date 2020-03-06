package com.ci.assignment.ui.category.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.ci.assignment.R
import com.ci.assignment.model.CategoryModel
import com.ci.assignment.ui.products.adapter.ProductsAdapter

class CategoryAdapter : RecyclerView.Adapter<CategoryAdapter.VideoListViewHolder>() {
    var categoryList: ArrayList<CategoryModel.ArrayOfProduct> = ArrayList()

    private var onClickListener: OnClickListener? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoListViewHolder {
        return VideoListViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.inflate_category_item,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return categoryList.size
    }

    override fun onBindViewHolder(holder: VideoListViewHolder, position: Int) {
        holder.onBind(categoryList[holder.adapterPosition])
    }


    inner class VideoListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imgCategory: ImageView = itemView.findViewById(R.id.imgCategory)
        private val tvCategoryTitle: TextView = itemView.findViewById(R.id.tvCategoryTitle)
        fun onBind(categoryModel: CategoryModel.ArrayOfProduct) {
            tvCategoryTitle.text = categoryModel.name
            Glide.with(imgCategory.context).load(categoryModel.imgUrl)
                .placeholder(getImagePlaceHolderLoading(imgCategory.context))
                .error(R.drawable.ic_placeholder)
                .into(imgCategory)
            itemView.setOnClickListener {
                onClickListener?.itemClicked(categoryModel)
            }
        }

    }

    fun addCategory(categoryList: ArrayList<CategoryModel.ArrayOfProduct>) {
        clearList()
        this.categoryList.addAll(categoryList)
        notifyDataSetChanged()
    }

    private fun clearList() {
        categoryList.clear()
        notifyDataSetChanged()
    }

    fun getImagePlaceHolderLoading(context: Context): CircularProgressDrawable {
        val circularProgressDrawable = CircularProgressDrawable(context)
        circularProgressDrawable.strokeWidth = 5f
        circularProgressDrawable.centerRadius = 30f
        circularProgressDrawable.start()
        return circularProgressDrawable
    }

    fun setOnClickListener(onClickListener: OnClickListener) {
        this.onClickListener = onClickListener
    }

    interface OnClickListener {
        fun itemClicked(categoryItem: CategoryModel.ArrayOfProduct)

    }
}