package com.ci.assignment.utils

import android.content.Context
import android.net.ConnectivityManager
import com.ci.assignment.MyApplication

object Network {
     fun isNetworkConnected(): Boolean {
        val cm = MyApplication.getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return cm.activeNetworkInfo != null
    }
}