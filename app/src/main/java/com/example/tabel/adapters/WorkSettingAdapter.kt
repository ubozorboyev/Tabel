package com.example.tabel.adapters

import android.content.Context
import android.content.DialogInterface
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.example.tabel.R
import com.example.tabel.ui.pageitems.WorkerItemData
import com.example.tabel.utils.Constant
import com.google.android.material.button.MaterialButton
import com.google.android.material.checkbox.MaterialCheckBox
import me.zhanghai.android.materialratingbar.MaterialRatingBar

class WorkSettingAdapter(val context: Context) :RecyclerView.Adapter<WorkSettingAdapter.ViewHolder>(){

     val workerList= arrayListOf<WorkerItemData>()
     var selectedListener:((Int)->Unit)?=null

    inner class ViewHolder(val view: View):RecyclerView.ViewHolder(view){

        val workerName=view.findViewById<MaterialCheckBox>(R.id.workerName)
        val matButton=view.findViewById<MaterialButton>(R.id.workerObjectName)
        val hour=view.findViewById<TextView>(R.id.workHour)
        val ratingBar=view.findViewById<MaterialRatingBar>(R.id.ratingBar)
        val radioGroup=view.findViewById<RadioGroup>(R.id.radioGroup)
        val inkrement=view.findViewById<ImageView>(R.id.inkement)
        val deInkrement=view.findViewById<ImageView>(R.id.deInkement)
        val helpImage=view.findViewById<MaterialButton>(R.id.helpImage)
        val frameLayout=view.findViewById<FrameLayout>(R.id.freamLayout)

        fun bind(){

            val worker=workerList[adapterPosition]
            workerName.text=worker.workName.second
            hour.text=worker.workerHour.toString()
            matButton.setText(worker.workerObName.second)
            workerName.isChecked=worker.isChecked
            frameLayout.visibility=if (workerName.isChecked) View.VISIBLE else View.INVISIBLE
            ratingBar.setRating(worker.workerRating.toFloat())
            ratingBar.layoutParams=LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT)

            ratingBar.setOnRatingBarChangeListener { ratingBar, rating, fromUser ->
                val rat=rating.toInt()
                Log.d("RATING","rating $rat")
                when(rat){
                    0->ratingBar.setRating(1f)
                    1->{ worker.workerRating=rat}
                    2->{ worker.workerRating=rat}
                    3->{ worker.workerRating=rat}
                    4->{ worker.workerRating=rat}
                    5->{ worker.workerRating=rat}
                }
            }


            workerName.setOnCheckedChangeListener { buttonView, isChecked ->
                frameLayout.visibility=if (isChecked) View.VISIBLE else View.INVISIBLE
                worker.isChecked=isChecked

            }

            matButton.setOnClickListener {
                selectedListener?.invoke(adapterPosition)
            }

            inkrement.setOnClickListener {
                if (worker.workerHour<Constant.maxHour){
                    animateView(it)
                    setHourText(++worker.workerHour)
                }
            }

            deInkrement.setOnClickListener {
             if (worker.workerHour>Constant.minHour) {
                 animateView(it)
                 setHourText(--worker.workerHour)
             }
            }

            helpImage.setOnClickListener {
                val dialog=AlertDialog.Builder(context)

                dialog.setTitle(context.getString(R.string.rating_title))
                dialog.setMessage(context.getString(R.string.rating_message))
                dialog.setPositiveButton("OK",object :DialogInterface.OnClickListener{
                    override fun onClick(dialog: DialogInterface?, which: Int) {
                        dialog?.dismiss()
                    }
                }).create().show()
            }

            radioGroup.setOnCheckedChangeListener { group, checkedId ->

                when(checkedId){
                    R.id.otprosilya->{
                        worker.vorkerEpsont=1
                    }
                    R.id.boleli->{
                        worker.vorkerEpsont=2
                    }
                    R.id.nе_prishel->{
                        worker.vorkerEpsont=3
                    }
                    else->{worker.vorkerEpsont=0}
                }
            }

        }

        fun setHourText(count:Int){
            hour.text=count.toString()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater=LayoutInflater.from(parent.context)
        val view=inflater.inflate(R.layout.pagetwo_item,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount()=workerList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int)=holder.bind()

    fun setData(ls:List<WorkerItemData>){
        workerList.clear()
        workerList.addAll(ls)
        notifyDataSetChanged()
    }

    fun animateView(view: View){
        view.alpha=0f
        view.animate().alpha(1f).setDuration(300).start()

    }

}