package com.example.tabel.viewmodel.pageviewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.tabel.model.*
import com.example.tabel.utils.Constant
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.time.LocalDate

class PageOneViewModel :ViewModel(){

    private val apiModel=ApiModel()
    private val _todayList=MutableLiveData<List<YesterdayaddedData>>()
    val todayList:LiveData<List<YesterdayaddedData>> =_todayList

    val isOnline:LiveData<Boolean> = Constant.isNetworkConnection

    private val _yestrdayList=MutableLiveData<List<YesterdayaddedData>>()
    val yestrdayList:LiveData<List<YesterdayaddedData>> =_yestrdayList


    fun loadTodayWorker(){

        apiModel.getAPi().loadTodayWorker("gettodayadded")
            .enqueue(object : Callback<ToDayAddModel>{
                override fun onFailure(call: Call<ToDayAddModel>, t: Throwable) {
                       t.printStackTrace()
                }

                override fun onResponse(call: Call<ToDayAddModel>, response: Response<ToDayAddModel>) {

                    if (response.isSuccessful){
                        Log.d("TTTT","TODAY RESPONSE ${response.body()}")
                        if (response.body()!=null){
                           _todayList.value=response.body()!!.todayadded

                        }
                    }
                }
            })
    }

    fun loadYesterdayWorker(){

        apiModel.getAPi().loadYesterdayWorker("getyesterdayadded").enqueue(object : Callback<YesterDayAddModel>{

                override fun onFailure(call: Call<YesterDayAddModel>, t: Throwable) {
                     t.printStackTrace()
                }

                override fun onResponse(call: Call<YesterDayAddModel>, response: Response<YesterDayAddModel>) {

                    if (response.isSuccessful){
                        Log.d("TTTT","DAY RESPONSE ${response.body()}")

                        if (response.body()!=null){
                            _yestrdayList.value=response.body()!!.yesterdayadded
                         }
                    }
                }
            })
    }
}