package com.ci.assignment.model


data class PriceModel(val min: Int, val max: Int, var isChecked: Boolean = false) {

    override fun toString(): String {
        return if (max == -1) {
            "$min and above"
        } else "$min-$max"
    }
}