package com.ci.videoplayertask.data

import com.ci.assignment.data.API
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object BaseRetrofit {
    private var retrofit: Retrofit? = null
    private val client: Retrofit?
        get() {

            val interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BODY
            val client = OkHttpClient.Builder().addInterceptor(interceptor).build()
            retrofit = Retrofit.Builder()
                .baseUrl("http://www.mocky.io/v2/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
            return retrofit
        }
    val apis : API?
    get() {
        return client?.create(API::class.java)
    }

}