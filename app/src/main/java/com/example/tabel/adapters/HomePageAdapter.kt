package com.example.tabel.adapters

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.tabel.R
import com.example.tabel.ui.pageitems.PageOneFragment
import com.example.tabel.ui.pageitems.PageThreeFragment
import com.example.tabel.ui.pageitems.PageTwoFragment

class HomePageAdapter(fragmentManager: FragmentManager,val context: Context) :FragmentPagerAdapter(fragmentManager){
    
    override fun getItem(position: Int): Fragment {

        return when(position){
            0->{
                PageOneFragment()
            }
            1->{
                PageTwoFragment()
            }
            2->{
                PageThreeFragment()
            }
            else->PageOneFragment()
        }
    }

    override fun getCount(): Int {
     return 3   
    }


    override fun getPageTitle(position: Int): CharSequence? {
        
        return when(position){
            0->context.getString(R.string.page0)
            1->context.getString(R.string.page1)
            2->context.getString(R.string.page2)
            else->null
        }
    }

}