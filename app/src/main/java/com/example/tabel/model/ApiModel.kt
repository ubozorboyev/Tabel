package com.example.tabel.model

import android.util.Log
import com.example.tabel.utils.Constant
import com.example.tabel.utils.LoginInterceptor
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class ApiModel {

    private val retrofitBuilder=Retrofit.Builder().baseUrl(Constant.BASE_URL).addConverterFactory(GsonConverterFactory.create())

    fun getAPi():ApiInterface{

        Log.d("TTT","login ${Constant.userName}")

            val clinet=OkHttpClient().newBuilder()
                .connectTimeout(2, TimeUnit.MINUTES)
                .writeTimeout(2, TimeUnit.MINUTES) // write timeout
                .readTimeout(2, TimeUnit.MINUTES)
                .addInterceptor(LoginInterceptor(Constant.userName,Constant.password))
                .build()

          retrofitBuilder.client(clinet)

        return retrofitBuilder.build().create(ApiInterface::class.java)
    }

}