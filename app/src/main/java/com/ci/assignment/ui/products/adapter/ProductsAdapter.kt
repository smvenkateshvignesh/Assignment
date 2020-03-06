package com.ci.assignment.ui.products.adapter

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
import com.ci.assignment.model.ProductsModel

class ProductsAdapter : RecyclerView.Adapter<ProductsAdapter.ProductListViewHolder>() {
    var categoryList: ArrayList<ProductsModel.ArrayOfProduct> = ArrayList()

    private var onClickListener: OnClickListener? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductListViewHolder {
        return ProductListViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.inflate_product_item,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return categoryList.size
    }

    override fun onBindViewHolder(holder: ProductListViewHolder, position: Int) {
        holder.onBind(categoryList[holder.adapterPosition])
    }


    inner class ProductListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imgProduct: ImageView = itemView.findViewById(R.id.imgProduct)
        private val tvProductName: TextView = itemView.findViewById(R.id.tvProductName)
        private val tvProductDec: TextView = itemView.findViewById(R.id.tvProductDec)
        private val tvProductPrice: TextView = itemView.findViewById(R.id.tvProductPrice)
        private val txtOutOfStock: TextView = itemView.findViewById(R.id.txtOutOfStock)
        fun onBind(categoryModel: ProductsModel.ArrayOfProduct) {
            tvProductName.text = categoryModel.name
            tvProductDec.text = categoryModel.description
            tvProductPrice.text ="Rs. ${categoryModel.price}"
            txtOutOfStock.visibility = if(categoryModel.quantity?:0 <= 0){
                View.VISIBLE
            }else{
                View.GONE
            }
            Glide.with(imgProduct.context).load(categoryModel.imgUrl)
                .placeholder(getImagePlaceHolderLoading(imgProduct.context))
                .error(R.drawable.ic_placeholder)
                .into(imgProduct)
            itemView.setOnClickListener {
                onClickListener?.itemClicked(categoryModel)
            }
        }

    }

    fun addCategory(categoryList: ArrayList<ProductsModel.ArrayOfProduct>) {
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
        fun itemClicked(product: ProductsModel.ArrayOfProduct)

    }
}