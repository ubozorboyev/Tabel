package com.example.tabel.ui.pageitems

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.tabel.R
import com.example.tabel.adapters.PageTreeAdapter
import com.example.tabel.databinding.PageTreeBinding
import com.example.tabel.ui.BaseFragment
import com.example.tabel.viewmodel.pageviewmodels.PageThreeViewModel

class PageThreeFragment :BaseFragment<PageTreeBinding>(R.layout.page_tree){

    private val adapter=PageTreeAdapter()

    private val viewmodel by viewModels<PageThreeViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        binding.recyclerview.adapter=adapter

        viewmodel.isOnline.observe(viewLifecycleOwner, Observer {
            if (it)viewmodel.loadWerkerList()
        })


        viewmodel.workers.observe(viewLifecycleOwner, Observer {
            adapter.setData(it)
        })

    }

}