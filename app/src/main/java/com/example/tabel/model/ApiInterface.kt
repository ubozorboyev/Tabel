package com.example.tabel.model

import com.example.tabel.ui.pageitems.WorkerItemData
import retrofit2.Call
import retrofit2.http.*

interface ApiInterface {

    @Headers("Content-Type: application/json")
    @GET(".")
    fun loginUser():Call<LoginResponse>

    @Headers("Content-Type: application/json")
    @GET(".")
    fun loadWorkers(@Query("mod")mod:String):Call<PageThreeModel>

    @Headers("Content-Type: application/json")
    @GET(".")
    fun loadTodayWorker(@Query("mod")mod:String):Call<ToDayAddModel>

    @Headers("Content-Type: application/json")
    @GET(".")
    fun loadYesterdayWorker(@Query("mod")mod:String):Call<YesterDayAddModel>

    @Headers("Content-Type: application/json")
    @GET(".")
    fun loadObjectNames(@Query("mod")mod: String):Call<ObjectModel>

    @GET(".")
    fun getHours(@Query("mod")mod: String):Call<HoursModel>

    @FormUrlEncoded
    @Headers("Content-Type: application/x-www-form-urlencoded")
    @POST(".")
    fun postDatas(@Query("mod")mod: String, @FieldMap map:MutableMap<String,Int>):Call<PostResponse>

    @Headers("Content-Type: application/json")
    @GET(".")
    fun getLastAdded(@Query("mod")mod: String):Call<LastAddedModel>

    @Headers("Content-Type: application/json")
    @GET(".")
    fun getNews(@Query("mod")mod: String):Call<NewsModel>



}