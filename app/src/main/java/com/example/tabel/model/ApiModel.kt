package com.example.tabel.model

import android.util.Log
import com.example.tabel.utils.Constant
import com.example.tabel.utils.LoginInterceptor
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiModel {

    private val retrofitBuilder=Retrofit.Builder().baseUrl(Constant.BASE_URL).addConverterFactory(GsonConverterFactory.create())

    fun getAPi():ApiInterface{

        Log.d("TTT","login ${Constant.userName}")

            val clinet=OkHttpClient().newBuilder()
                .addInterceptor(LoginInterceptor(Constant.userName,Constant.password))
                .build()

          retrofitBuilder.client(clinet)

        return retrofitBuilder.build().create(ApiInterface::class.java)
    }

}