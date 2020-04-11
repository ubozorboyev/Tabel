package com.example.tabel.ui.pageitems

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.SeekBar
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
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

class PageTwoFragment :BaseFragment<PageTwoBinding>(R.layout.page_two){

    private val adapter by lazy{WorkSettingAdapter(context!!)}
    private val viewmodel by viewModels<PageTwoViewModel>()
    private var objectList = mutableMapOf<Int,String>()
    private val dialogSweet by lazy { SweetAlertDialog(context) }
    private var lastaddeds= listOf<Lastadded>()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        binding.recyclerview.adapter=adapter
        obseravbleSettings()
        adapterListeners()
        binding.fabSave.setOnClickListener {
            postData()
        }

        viewmodel.isOnline.observe(viewLifecycleOwner, Observer {
            if (it){
                init()
            }else{
                dialogSweet.dismiss()
                Snackbar.make(view,Constant.networkNoConnection,Snackbar.LENGTH_LONG).show()
            }
        })
    }

    fun init(){
        viewmodel.loadObjectNames()
        viewmodel.loadLastAdd()
        viewmodel.getTodayAdd()
        objectNamesObseravble()
        lastAddedObseravble()
        workerObseravble()
        statusSave()
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

    fun obseravbleSettings(){

        dialogSweet.changeAlertType(SweetAlertDialog.PROGRESS_TYPE)
        dialogSweet.setTitleText("Loadding...")
        dialogSweet.setCancelable(true)
        dialogSweet.show()

        viewmodel.message.observe(viewLifecycleOwner, Observer {
            dialogSweet.dismiss()
            dialogSweet.changeAlertType(SweetAlertDialog.ERROR_TYPE)
            dialogSweet.setConfirmButton("Ok",object:SweetAlertDialog.OnSweetClickListener {
                override fun onClick(sweetAlertDialog: SweetAlertDialog?) {
                    sweetAlertDialog?.dismiss()
                }
            }).show()
            dialogSweet.setTitle(it)
        })

    }

    fun postData(){

        val ls= mutableMapOf<String,Int>()
        var count=0
        adapter.workerList.forEach {
            if (it.isChecked && !it.workerObName.second.equals("Select Object")){

                if (it.vorkerEpsont==0){
                    ls.put("workerid[$count]",it.workName.first)
                    ls.put("object${it.workName.first}",it.workerObName.first)
                    ls.put("hour${it.workName.first}",it.workerHour)
                    ls.put("rating${it.workName.first}",it.workerRating)
                    count++
                }else{
                    ls.put("workerid[$count]",it.workName.first)
                    ls.put("epsend${it.workName.first}",it.vorkerEpsont)

                }
            }
        }
        if (ls.isNotEmpty()){
            dialogSweet.setTitleText("")
            dialogSweet.setContentText("")
            dialogSweet.changeAlertType(SweetAlertDialog.PROGRESS_TYPE)
            dialogSweet.show()
            viewmodel.postData(ls)
            viewmodel.loadLastAdd()
        }
    }


    fun workerObseravble(){
        viewmodel.workers.observe(viewLifecycleOwner, Observer {
            dialogSweet.dismiss()

            val filterList=it.filter {
                val worker=it

                lastaddeds.forEach {

                    if (worker.workName.first==it.worker_id){
                        worker.workerObName= Pair(it.objectid,objectList.get(it.objectid)?:"Select Object")
                    }
                }

                SharedPreferense.getTodayWorkers()?.contains(it.workName.second)?.not()?:true
            }
            adapter.setData(filterList)
        })
    }

    fun objectNamesObseravble(){
        viewmodel.objectNames.observe(viewLifecycleOwner, Observer {
            objectList.clear()
            Log.d("SSSS","objectNamesObseravble $it")

            it.forEach { objectList.put(it.objectid,it.objectname) }
            viewmodel.loadWorker()
        })

    }

    fun lastAddedObseravble(){

        viewmodel.lastadded.observe(viewLifecycleOwner, Observer {
            Log.d("SSSS","lastAddedObseravble $it")
            lastaddeds=it
        })
    }

    fun statusSave(){

        viewmodel.statusSave.observe(viewLifecycleOwner, Observer { it ->

            viewmodel.getTodayAdd()
            dialogSweet.dismiss()
            dialogSweet.changeAlertType(SweetAlertDialog.SUCCESS_TYPE)
            dialogSweet.setTitleText("Success")
            dialogSweet.setContentText(it.toString()).setCancelable(false)
            dialogSweet.setConfirmButton("OK",object :SweetAlertDialog.OnSweetClickListener{
                override fun onClick(sweetAlertDialog: SweetAlertDialog?) {
                    viewmodel.loadWorker()
                    sweetAlertDialog?.dismiss()
                }
            }).show()
        })
    }
/*

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (isVisibleToUser){
            try {
                init()
            }catch (e:IllegalStateException){
                e.printStackTrace()
            }
        }
    }
*/

}


data class WorkerItemData(
    val workName:Pair<Int,String>,
    var workerObName:Pair<Int,String>,
    var workerHour:Int,
    var workerRating:Int,
    var vorkerEpsont:Int=0,
    var isChecked:Boolean=false
)