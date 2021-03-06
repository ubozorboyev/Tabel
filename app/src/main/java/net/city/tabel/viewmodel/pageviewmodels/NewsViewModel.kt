package net.city.tabel.viewmodel.pageviewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import net.city.tabel.model.ApiModel
import net.city.tabel.model.New
import net.city.tabel.model.NewsModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NewsViewModel :ViewModel(){

    private val apiModel= ApiModel()

    private val _news= MutableLiveData<List<New>>()
    val news:LiveData<List<New>> =_news

    fun loadNews(){
        apiModel.getAPi().getNews("getnews").enqueue(object : Callback<NewsModel>{
            override fun onFailure(call: Call<NewsModel>, t: Throwable) {

            }

            override fun onResponse(call: Call<NewsModel>, response: Response<NewsModel>) {
                if (response.isSuccessful){
                    if (response.body()!=null){
                        _news.value=response.body()!!.news
                    }
                }

            }
        })
    }

}