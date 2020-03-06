package com.ci.assignment.ui.filter

import android.app.Activity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.RadioButton
import androidx.appcompat.app.AppCompatActivity
import com.ci.assignment.R
import com.ci.assignment.model.PriceModel
import kotlinx.android.synthetic.main.activity_filter.*
import java.util.*

class FilterActivity : AppCompatActivity(), View.OnClickListener {

    companion object {
        val priceModels = ArrayList<PriceModel>().apply {
            add(PriceModel(1, 100))
            add(PriceModel(100, 500))
            add(PriceModel(500, -1))
        }
        val colors = ArrayList<String>().apply {
            add("Red")
            add("Blue")
            add("White")
        }
        var colorSelection = -1
        var inStock = false

        fun clearSharedPreference() {
            inStock = false
            colorSelection = -1
            clearPriceCheck()
        }
        private fun clearPriceCheck() {
            for (model in priceModels) {
                model.isChecked = false
            }
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_filter)
        showBackIcon()
        setPriceViews()
        btnClearFilters.setOnClickListener(this)
        btnApplyFilters.setOnClickListener(this)
        setupData()
    }

    private fun showBackIcon() {
        supportActionBar?.title = "Filter"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
    }

    private fun setupData() {
        cbStock.isChecked = inStock
        if (colorSelection != -1) {
            radioGroupColor.check(colorSelection)
        }
        for ((index, model) in priceModels.withIndex()) {
            if (model.isChecked) {
                radioGroupPrice.check(index)
                break
            }
        }
    }

    private fun onApplyFilters() {
        inStock = cbStock.isChecked
        colorSelection = radioGroupColor.checkedRadioButtonId
        setResult(Activity.RESULT_OK)
        finish()
    }

    private fun setPriceViews() {
        for ((index, model) in priceModels.withIndex()) {
            val radioButton = RadioButton(this)
            radioButton.id = index
            radioButton.text = model.toString()
            radioGroupPrice.addView(radioButton)
        }
        for ((index, model) in colors.withIndex()) {
            val radioButton = RadioButton(this)
            radioButton.id = index
            radioButton.text = model
            radioGroupColor.addView(radioButton)
        }
        radioGroupPrice.setOnCheckedChangeListener { group, checkedId ->
            clearPriceCheck()
            if (checkedId in 0 until priceModels.size)
                priceModels[checkedId].isChecked = true
        }

        radioGroupColor.setOnCheckedChangeListener { group, checkedId ->
            colorSelection = checkedId
        }
    }

    private fun clearFilters() {
        radioGroupColor.clearCheck()
        radioGroupPrice.clearCheck()
        cbStock.isChecked = false
        clearSharedPreference()
    }




    override fun onClick(v: View?) {
        when (v) {
            btnClearFilters -> {
                clearFilters()
            }
            btnApplyFilters -> {
                onApplyFilters()
            }

        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        setResult(Activity.RESULT_OK)
        finish()
    }
}
