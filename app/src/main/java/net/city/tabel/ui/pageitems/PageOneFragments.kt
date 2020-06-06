package net.city.tabel.ui.pageitems

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import net.city.tabel.R
import net.city.tabel.ui.BaseFragment
import net.city.tabel.viewmodel.pageviewmodels.PageOneViewModel
import kotlinx.android.synthetic.main.title_layout.view.*
import net.city.tabel.adapters.DayAddAdpter
import net.city.tabel.databinding.PageOneBinding

class PageOneFragment : BaseFragment<PageOneBinding>(R.layout.page_one){

    private val todAdapter= DayAddAdpter()
    private val yesAdapter= DayAddAdpter()

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

        binding.appTitle.pagetite.text=getString(R.string.page0)

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