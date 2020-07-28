package net.city.tabel.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment

abstract class BaseFragment<DB:ViewDataBinding>(@LayoutRes val resId:Int) :Fragment(){

    lateinit var binding: DB

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //return super.onCreateView(inflater, container, savedInstanceState)

        binding=DataBindingUtil.inflate(inflater,resId,container,false)
        return binding.root
    }

}

interface FragmentLifecycle{

    fun onPauseFragment()
    fun onResumeFragment()

}