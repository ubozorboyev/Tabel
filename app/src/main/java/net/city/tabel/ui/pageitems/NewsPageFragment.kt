package net.city.tabel.ui.pageitems

import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import net.city.tabel.adapters.NewsAdapter
import net.city.tabel.dialogs.UpdateDialog
import net.city.tabel.model.New
import net.city.tabel.ui.BaseFragment
import net.city.tabel.R
import net.city.tabel.databinding.NewsFragmentBinding
import net.city.tabel.viewmodel.pageviewmodels.NewsViewModel
import java.util.*

class NewsPageFragment : BaseFragment<NewsFragmentBinding>(R.layout.news_fragment){

    private val adapter=NewsAdapter()

    private val viewmodel by viewModels<NewsViewModel>()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        binding.swiperRefresh.isRefreshing=true
        binding.recyclerview.adapter=adapter
        viewmodel.loadNews()
        obserableNews()
        refreshData()
    }



    /*fun showProgress(){

        var processState=0
        val handler=Handler()

        val dialog=UpdateDialog(context!!)
        dialog.setMaxProgress(1024*7)
        dialog.show()

        Thread(Runnable {
            kotlin.run {

                while (processState<1024*7) {

                    processState += 650
                    Log.d("DDDDDDD","processState ${processState}")

                    handler.post(Runnable {

                        kotlin.run {
                            dialog.setTodaldownlanded(processState.toLong())
                        }

                    })

                    if(processState >= 1024*7) dialog.dismiss()

                    Thread.sleep(500)

                }
            }

        }).start()
    }*/



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