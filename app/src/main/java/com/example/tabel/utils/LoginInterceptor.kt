package com.example.tabel.utils

import okhttp3.Interceptor

class LoginInterceptor(username:String, password:String) :Interceptor {

    private val credentials= okhttp3.Credentials.basic(username,password)

    override fun intercept(chain: Interceptor.Chain): okhttp3.Response {
        val request=chain.request().newBuilder()
//            .addHeader("Content-Type", "application/json")
            .header("Authorization",credentials).build()

        return chain.proceed(request)
    }


}