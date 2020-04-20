package com.example.tabel.ui

import android.os.Bundle
import android.view.View
import androidx.activity.addCallback
import com.example.tabel.R
import com.example.tabel.adapters.HomePageAdapter
import com.example.tabel.databinding.HomeFragmnetBinding

class HomeFragment :BaseFragment<HomeFragmnetBinding>(R.layout.home_fragmnet){

    private val adapter by lazy { HomePageAdapter(activity?.supportFragmentManager!!,context!!) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        binding.viewpager.adapter=adapter
        binding.tabLayout.setupWithViewPager(binding.viewpager)
        setTabIcon()

        requireActivity().onBackPressedDispatcher.addCallback(this){
            activity?.finish()
        }
    }

    fun setTabIcon(){
        binding.tabLayout.getTabAt(0)?.setIcon(R.drawable.page1)
        binding.tabLayout.getTabAt(1)?.setIcon(R.drawable.page2)
        binding.tabLayout.getTabAt(2)?.setIcon(R.drawable.page3)
        binding.tabLayout.getTabAt(3)?.setIcon(R.drawable.page4)
    }

}