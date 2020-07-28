package net.city.tabel.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import net.city.tabel.model.ApiModel
import net.city.tabel.model.LoginResponse
import net.city.tabel.utils.Constant
import net.city.tabel.utils.SharedPreferense
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginViewModel  :ViewModel(){

    private val api= ApiModel()
    private val _userId=MutableLiveData<Int>()
    var userId:LiveData<Int> =_userId

    private val _statusError=MutableLiveData<String>()
    val statusError:LiveData<String> =_statusError
    fun loginUser(){

        api.getAPi().loginUser().enqueue(object :Callback<LoginResponse>{
            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                Log.d("TTTT","throwable $t")
            }

            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {

                if (response.isSuccessful && response.body()!=null){

                    val body=response.body()!!

                    if (body.status_auth.equals("Ok",true)) {
                        _userId.value=body.userid
                         setPreferensData()
                    }else{
                        _statusError.value=body.status_auth
                    }
                    Log.d("TTTT","body ${response.body()}")
                }
            }
        })
    }



    fun setPreferensData(){
        SharedPreferense.setLogin(Constant.userName)
        SharedPreferense.setPassword(Constant.password)
    }

}