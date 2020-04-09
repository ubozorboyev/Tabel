package com.example.tabel.ui.pageitems

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.tabel.R
import com.example.tabel.adapters.DayAddAdpter
import com.example.tabel.databinding.PageOneBinding
import com.example.tabel.ui.BaseFragment
import com.example.tabel.viewmodel.pageviewmodels.PageOneViewModel

class PageOneFragment :BaseFragment<PageOneBinding>(R.layout.page_one){

    private val todAdapter=DayAddAdpter()
    private val yesAdapter=DayAddAdpter()

    private val viewmodel by viewModels<PageOneViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        binding.todRecyclerview.adapter=todAdapter
        binding.yesRecyclerview.adapter=yesAdapter

        viewModelOperation()

        viewmodel.isOnline.observe(viewLifecycleOwner, Observer {
            if (it){
                viewmodel.loadTodayWorker()
                viewmodel.loadYesterdayWorker()
            }
        })

    }

    fun viewModelOperation(){

        viewmodel.todayList.observe(viewLifecycleOwner, Observer {
            todAdapter.setData(it)
        })

        viewmodel.yestrdayList.observe(viewLifecycleOwner, Observer {
            yesAdapter.setData(it)
        })
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (isVisibleToUser){
            try {
                viewmodel.loadTodayWorker()
            }catch (e:IllegalStateException){
                e.printStackTrace()
            }
        }
    }

}