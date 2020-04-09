package com.example.tabel.viewmodel.pageviewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.tabel.model.ApiModel
import com.example.tabel.model.PageThreeModel
import com.example.tabel.model.Worker
import com.example.tabel.utils.Constant
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PageThreeViewModel :ViewModel(){

    private val api=ApiModel()
    private val _workers=MutableLiveData<List<Worker>>()
    val workers:LiveData<List<Worker>> =_workers

    val isOnline:LiveData<Boolean> =Constant.isNetworkConnection

    fun loadWerkerList(){

        api.getAPi().loadWorkers("getmyworkers")
            .enqueue(object :Callback<PageThreeModel>{
                override fun onFailure(call: Call<PageThreeModel>, t: Throwable) {

                }

                override fun onResponse( call: Call<PageThreeModel>, response: Response<PageThreeModel>) {

                    if (response.isSuccessful){

                        Log.d("TTT","response ${response.body()}")

                        if (response.body()!=null){
                            val body=response.body()
                            _workers.value=body!!.workers
                        }

                    }
                }
            })

    }
}