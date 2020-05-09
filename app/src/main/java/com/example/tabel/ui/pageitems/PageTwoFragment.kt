package com.example.tabel.ui.pageitems

import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.SeekBar
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import cn.pedant.SweetAlert.SweetAlertDialog
import com.example.tabel.R
import com.example.tabel.adapters.WorkSettingAdapter
import com.example.tabel.databinding.PageTwoBinding
import com.example.tabel.dialogs.BaseInterface
import com.example.tabel.dialogs.ChoseItemDialog
import com.example.tabel.model.Lastadded
import com.example.tabel.model.Object
import com.example.tabel.model.StatusSave
import com.example.tabel.ui.BaseFragment
import com.example.tabel.utils.Constant
import com.example.tabel.utils.SharedPreferense
import com.example.tabel.viewmodel.pageviewmodels.PageTwoViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.title_layout.view.*

class PageTwoFragment :BaseFragment<PageTwoBinding>(R.layout.page_two){

    private val adapter by lazy{WorkSettingAdapter(context!!)}
    private val viewmodel by viewModels<PageTwoViewModel>()
    private var objectList = mutableMapOf<Int,String>()
    private val dialogSweet by lazy { SweetAlertDialog(context) }
    private var lastaddeds= listOf<Lastadded>()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        binding.appTitle.pagetite.text=getString(R.string.page1)

        binding.swiperRefresh.isRefreshing=true

        binding.recyclerview.adapter=adapter
        obseravbleSettings()
        adapterListeners()
        dialogSweet.hideConfirmButton()
        binding.fabSave.setOnClickListener { postData() }
        swipeRefresh()
    }

    fun swipeRefresh(){

        binding.swiperRefresh.setColorSchemeResources(android.R.color.holo_blue_bright,
            android.R.color.holo_green_light,
            android.R.color.holo_orange_light,
            android.R.color.holo_red_light)

        binding.swiperRefresh.setOnRefreshListener {
            adapter.setData(null)
            binding.emptyTextView.visibility=View.INVISIBLE
            loadData()
        }
    }

    fun loadData(){
        Log.d("XXXX","LoadData")
        viewmodel.loadObjectNames()
        viewmodel.loadLastAdd()
        viewmodel.getTodayAdd()
    }

    fun obseravbleSettings(){

        objectNamesObseravble()
        lastAddedObseravble()
        workerObseravble()
        messageObseravble()
        statusSave()

        viewmodel.isOnline.observe(viewLifecycleOwner, Observer {
            if (it && viewmodel.workers.value.isNullOrEmpty()) loadData()
        })
    }

    fun adapterListeners(){

        adapter.selectedListener=object:(Int)->Unit{

            override fun invoke(p1: Int) {

                val obNames= arrayListOf<String>()
                val keys= arrayListOf<Int>()

                objectList.forEach {
                    keys.add(it.key)
                    obNames.add(it.value)
                }

                val dialog=ChoseItemDialog(context!!,obNames.toList().toTypedArray(),
                object :BaseInterface{

                    override fun choseItemDialog(position: Int) {

                        val pair=Pair(keys[position],obNames[position])

                        adapter.workerList[p1].workerObName=pair

                        adapter.notifyItemChanged(p1)
                    }
                })
                dialog.show()
            }
        }
    }

    fun messageObseravble(){

        viewmodel.message.observe(viewLifecycleOwner, Observer {
            if (it!=null){
                dialogSweet.dismiss()
                dialogSweet.changeAlertType(SweetAlertDialog.ERROR_TYPE)
                dialogSweet.setTitle(it)
                dialogSweet.show()
                viewmodel.clearMessageStatus()
            }
        })
    }

    fun statusSave(){

        viewmodel.statusSave.observe(viewLifecycleOwner, Observer {

            if (it!=null){
                viewmodel.getTodayAdd()
                Log.d("XXXXXX","save status")

                dialogSweet.changeAlertType(SweetAlertDialog.SUCCESS_TYPE)
                dialogSweet.setTitleText("Save")
                dialogSweet.show()
                viewmodel.loadWorker()
                viewmodel.clearMessageStatus()
            }
        })
    }

    fun postData(){

        val ls= mutableMapOf<String,Int>()
        var count=0

        adapter.workerList.forEach {

            if (it.isChecked ){

                if (it.vorkerEpsont==0 && !it.workerObName.second.equals("Select Object")){
                    ls.put("workerid[$count]",it.workName.first)
                    ls.put("object${it.workName.first}",it.workerObName.first)
                    ls.put("hour${it.workName.first}",it.workerHour)
                    ls.put("rating${it.workName.first}",it.workerRating)
                    count++

                }else if (it.vorkerEpsont>0){
                    ls.put("workerid[$count]",it.workName.first)
                    ls.put("epsend${it.workName.first}",it.vorkerEpsont)
                }
            }
        }

        if (ls.isNotEmpty()){
            showProgress()
            viewmodel.postData(ls)
            viewmodel.loadLastAdd()
        }
    }


    fun workerObseravble(){

        viewmodel.workers.observe(viewLifecycleOwner, Observer {

            dialogSweet.dismiss()
            binding.swiperRefresh.isRefreshing=false
            Log.d("XXXXXXXXXXX","list worker $it")

                 val filterList=it.filter {

                     val worker=it

                     lastaddeds.forEach {

                     if (worker.workName.first==it.worker_id)
                         worker.workerObName= Pair(it.objectid,objectList.get(it.objectid)?:"Select Object")

                     }

                SharedPreferense.getTodayWorkers()?.contains(it.workName.second)?.not()?:true
            }

            binding.emptyTextView.visibility=if (filterList.isNullOrEmpty()) View.VISIBLE else View.INVISIBLE

            adapter.setData(filterList)
        })
    }

    fun objectNamesObseravble(){

        viewmodel.objectNames.observe(viewLifecycleOwner, Observer {
            objectList.clear()
            it.forEach { objectList.put(it.objectid,it.objectname) }
            viewmodel.loadWorker()
        })

    }

    fun lastAddedObseravble(){

        viewmodel.lastadded.observe(viewLifecycleOwner, Observer {
            lastaddeds=it
        })
    }

    fun showProgress(){

        if (isVisible){
            dialogSweet.changeAlertType(SweetAlertDialog.PROGRESS_TYPE)
            dialogSweet.setTitleText("Loadding...")
            dialogSweet.setCancelable(true)
            dialogSweet.show()
        }
    }

}

data class WorkerItemData(
    val workName:Pair<Int,String>,
    var workerObName:Pair<Int,String>,
    var workerHour:Int,
    var workerRating:Int,
    var vorkerEpsont:Int=0,
    var isChecked:Boolean=false
)