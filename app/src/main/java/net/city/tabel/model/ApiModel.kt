package net.city.tabel.model

import net.city.tabel.utils.Constant
import net.city.tabel.utils.LoginInterceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class ApiModel {

    private val retrofitBuilder=Retrofit.Builder().baseUrl(Constant.BASE_URL).addConverterFactory(GsonConverterFactory.create())

    fun getAPi():ApiInterface {

            val clinet=OkHttpClient().newBuilder()
                .connectTimeout(2, TimeUnit.MINUTES)
                .writeTimeout(2, TimeUnit.MINUTES) // write timeout
                .readTimeout(2, TimeUnit.MINUTES)
                .addInterceptor(LoginInterceptor(
                    Constant.userName,
                    Constant.password)
                )
                .build()

          retrofitBuilder.client(clinet)

        return retrofitBuilder.build().create(ApiInterface::class.java)
    }

}