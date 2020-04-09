package com.example.tabel.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.tabel.R
import com.example.tabel.model.YesterdayaddedData

class DayAddAdpter :RecyclerView.Adapter<DayAddAdpter.ViewHolder>(){

   private val dayAddList=ArrayList<YesterdayaddedData>()

    inner class ViewHolder(view: View):RecyclerView.ViewHolder(view){
        val name=view.findViewById<TextView>(R.id.workername)
        val objectName=view.findViewById<TextView>(R.id.objectName)

        fun bind(){
          name.text=dayAddList[adapterPosition].workername
          objectName.text=dayAddList[adapterPosition].objectname
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater=LayoutInflater.from(parent.context)
        val view=inflater.inflate(R.layout.dayadd_item,parent,false)
        return ViewHolder(view)

    }

    override fun getItemCount()=dayAddList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int)=holder.bind()

    fun setData(ls:List<YesterdayaddedData>?){
        dayAddList.clear()
        if (ls!=null)  dayAddList.addAll(ls)
        notifyDataSetChanged()
    }

}