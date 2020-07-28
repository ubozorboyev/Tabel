package net.city.tabel.ui.pageitems

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import net.city.tabel.R
import net.city.tabel.ui.BaseFragment
import net.city.tabel.viewmodel.pageviewmodels.PageThreeViewModel
import kotlinx.android.synthetic.main.title_layout.view.*
import net.city.tabel.adapters.PageTreeAdapter
import net.city.tabel.databinding.PageTreeBinding

class PageThreeFragment : BaseFragment<PageTreeBinding>(R.layout.page_tree),SwipeRefreshLayout.OnRefreshListener{

    private val adapter= PageTreeAdapter()

    private val viewmodel by viewModels<PageThreeViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        binding.recyclerview.adapter=adapter
        binding.swiperRefresh.isRefreshing=true

        binding.appTitle.pagetite.text=getString(R.string.page2)

        viewmodel.isOnline.observe(viewLifecycleOwner, Observer {
            if (it && viewmodel.workers.value.isNullOrEmpty())viewmodel.loadWerkerList()
        })

        viewmodel.workers.observe(viewLifecycleOwner, Observer {
            adapter.setData(it)
            binding.swiperRefresh.isRefreshing=false
        })

        binding.swiperRefresh.setOnRefreshListener(this)
        binding.swiperRefresh.setColorSchemeResources(android.R.color.holo_blue_bright,
            android.R.color.holo_green_light,
            android.R.color.holo_orange_light,
            android.R.color.holo_red_light)
    }

    override fun onRefresh() {
        adapter.setData(null)
        viewmodel.loadWerkerList()
    }

}