package com.example.tabel.ui.pageitems

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.tabel.R
import com.example.tabel.adapters.NewsAdapter
import com.example.tabel.databinding.NewsFragmentBinding
import com.example.tabel.model.New
import com.example.tabel.ui.BaseFragment
import com.example.tabel.viewmodel.pageviewmodels.NewsViewModel
import java.util.*

class NewsPageFragment :BaseFragment<NewsFragmentBinding>(R.layout.news_fragment){

    private val adapter=NewsAdapter()

    private val viewmodel by viewModels<NewsViewModel>()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        binding.swiperRefresh.isRefreshing=true
        binding.recyclerview.adapter=adapter
        viewmodel.loadNews()
        obserableNews()
        refreshData()
    }

    fun obserableNews(){
        val newsObseravble= Observer<List<New>>{
            adapter.setData(it)
            binding.swiperRefresh.isRefreshing=false
        }
        viewmodel.news.observe(viewLifecycleOwner,newsObseravble)
    }

    fun refreshData(){
        binding.swiperRefresh.setColorSchemeResources(android.R.color.holo_blue_bright,
            android.R.color.holo_green_light,
            android.R.color.holo_orange_light,
            android.R.color.holo_red_light)

        binding.swiperRefresh.setOnRefreshListener {
            adapter.setData(null)
            viewmodel.loadNews()
        }

    }
}