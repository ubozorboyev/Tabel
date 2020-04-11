package com.example.tabel.viewmodel.pageviewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import cn.pedant.SweetAlert.SweetAlertDialog
import com.example.tabel.model.*
import com.example.tabel.ui.pageitems.WorkerItemData
import com.example.tabel.utils.Constant
import com.example.tabel.utils.SharedPreferense
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PageTwoViewModel :ViewModel(){


    private val apiModel=ApiModel()

    private val _objectNames=MutableLiveData<List<Object>>()
    val  objectNames:LiveData<List<Object>> =_objectNames

    private val _statusSave=MutableLiveData<String>()
    val statusSave:LiveData<String> =_statusSave

    private val _message=MutableLiveData<String>()
    val message:LiveData<String> =_message

    private val _workers=MutableLiveData<List<WorkerItemData>>()
    val workers:LiveData<List<WorkerItemData>> =_workers

    private val _lastAdded=MutableLiveData<List<Lastadded>>()
    val lastadded:LiveData<List<Lastadded>> =_lastAdded

    val isOnline:LiveData<Boolean> =Constant.isNetworkConnection

    fun loadObjectNames(){

        apiModel.getAPi().loadObjectNames("getobjects")
            .enqueue(object : Callback<ObjectModel>{
                override fun onFailure(call: Call<ObjectModel>, t: Throwable) {
                    t.printStackTrace()
                }

                override fun onResponse(call: Call<ObjectModel>, response: Response<ObjectModel>) {

                    if (response.isSuccessful){

                        if (response.body()!=null){
                         _objectNames.value=response.body()?.objects
                        }
                    }
                }
            })
    }


    fun loadWorker(){
        apiModel.getAPi().loadWorkers("getmyworkers")
            .enqueue(object :Callback<PageThreeModel>{
                override fun onFailure(call: Call<PageThreeModel>, t: Throwable) {
                    t.printStackTrace()
                }

                override fun onResponse(call: Call<PageThreeModel>, response: Response<PageThreeModel>) {

                    if (response.isSuccessful){

                        if (response.body()!=null){
                            val d=ArrayList<WorkerItemData>()

                            response.body()!!.workers.forEach {
                                d.add(WorkerItemData(Pair(it.workerid,it.workername),
                                    Pair(0,"Select object"),8,4))
                            }
                            _workers.value=d
                        }
                        else{
                            _message.value="response is not succsesful"
                        }
                    }
                }
            })
    }


    fun postData(map: MutableMap<String,Int>){

        apiModel.getAPi().postDatas("save",map)
            .enqueue(object :Callback<PostResponse>{
                override fun onFailure(call: Call<PostResponse>, t: Throwable) {
                   t.printStackTrace()
                }

                override fun onResponse(call: Call<PostResponse>, response: Response<PostResponse>) {

                    Log.d("FFF","response body ${response.body()}")

                    if (response.isSuccessful){

                        if (response.body()!=null){
                            var saved=true
                            val ls=response.body()!!.status_save

                            ls.forEach {
                                if (!it.status_add_db.equals("Save",true)) {
                                    _message.value="Error"
                                      saved=false
                                     return@forEach
                                }
                            }
                            if (saved) _statusSave.value="Saved"

                        }else{
                            _message.value=response.message()
                        }
                    }
                    else{
                        _message.value="response is not succsesful"
                    }

                }
            })
    }

    fun getTodayAdd(){
        apiModel.getAPi().loadTodayWorker(mod="gettodayadded").enqueue(object :Callback<ToDayAddModel>{
            override fun onFailure(call: Call<ToDayAddModel>, t: Throwable) {
              t.printStackTrace()
            }

            override fun onResponse(call: Call<ToDayAddModel>, response: Response<ToDayAddModel>) {
                if (response.isSuccessful && response.body()!=null){
                    val d= arrayListOf<String>()
                    response.body()?.todayadded?.forEach {
                        d.add(it.workername)
                    }
                    SharedPreferense.setTodayWorkers(d)
                }
            }
        })
    }

    fun loadLastAdd(){
        apiModel.getAPi().getLastAdded("getlastadded")
            .enqueue(object :Callback<LastAddedModel>{
                override fun onFailure(call: Call<LastAddedModel>, t: Throwable) {
                    t.printStackTrace()
                }

                override fun onResponse(call: Call<LastAddedModel>, response: Response<LastAddedModel>
                ) {

                    if (response.isSuccessful){
                        if (response.body()!=null){
                            _lastAdded.value=response.body()!!.lastadded
                        }
                    }
                }

            })
    }

}