package com.example.tabel.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.navigation.fragment.findNavController
import androidx.viewpager.widget.ViewPager
import com.example.tabel.R
import com.example.tabel.adapters.HomePageAdapter
import com.example.tabel.databinding.HomeFragmnetBinding

class HomeFragment :BaseFragment<HomeFragmnetBinding>(R.layout.home_fragmnet){

    private val adapter by lazy { HomePageAdapter(activity?.supportFragmentManager!!,context!!) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        binding.viewpager.adapter=adapter
        binding.tabLayout.setupWithViewPager(binding.viewpager)

        requireActivity().onBackPressedDispatcher.addCallback(this){
            activity?.finish()
        }

        binding.viewpager.addOnPageChangeListener(object :ViewPager.OnPageChangeListener{
            override fun onPageScrollStateChanged(state: Int) {
                Log.d("TTTT","onPageScrollStateChanged $state")
            }

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                Log.d("TTTT","onPageScrolled $position")
            }

            override fun onPageSelected(position: Int) {
                Log.d("TTTT","onPageSelected $position")
            }

        })

    }



}