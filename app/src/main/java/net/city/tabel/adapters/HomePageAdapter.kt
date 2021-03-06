package net.city.tabel.adapters

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import net.city.tabel.ui.pageitems.NewsPageFragment
import net.city.tabel.ui.pageitems.PageOneFragment
import net.city.tabel.ui.pageitems.PageThreeFragment
import net.city.tabel.ui.pageitems.PageTwoFragment

class HomePageAdapter(fragmentManager: FragmentManager,val context: Context) :FragmentPagerAdapter(fragmentManager){
    
    override fun getItem(position: Int): Fragment {

        return when(position){

            0 -> PageOneFragment()

            1 -> PageTwoFragment()

            2 -> PageThreeFragment()

            3 -> NewsPageFragment()

            else-> NewsPageFragment()
        }
    }

    override fun getCount(): Int {
        return 4
    }


    override fun getPageTitle(position: Int): CharSequence? {
        
        return  null/*when(position) {
            0 -> context.getString(R.string.page0)
            1 -> context.getString(R.string.page1)
            2 -> context.getString(R.string.page2)
            3 -> context.getString(R.string.page3)
            else -> null
        }*/
    }

}