package net.city.tabel.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import net.city.tabel.R
import net.city.tabel.model.YesterdayaddedData

class DayAddAdpter :RecyclerView.Adapter<DayAddAdpter.ViewHolder>(){

   private val dayAddList=ArrayList<YesterdayaddedData>()

    inner class ViewHolder(view: View):RecyclerView.ViewHolder(view){
        val name=view.findViewById<TextView>(R.id.workername)
        val objectName=view.findViewById<TextView>(R.id.objectName)

        fun bind(data: YesterdayaddedData){
          name.text = data.workername
          objectName.text = if (data.extend.isNullOrEmpty()){
              data.objectname
          }else{
              data.extend
          }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DayAddAdpter.ViewHolder {
        val inflater=LayoutInflater.from(parent.context)
        val view=inflater.inflate(R.layout.dayadd_item,parent,false)
        return ViewHolder(view)

    }

    override fun getItemCount()=dayAddList.size

    override fun onBindViewHolder(holder: DayAddAdpter.ViewHolder, position: Int)=holder.bind(dayAddList[position])

    fun setData(ls:List<YesterdayaddedData>?){
        dayAddList.clear()
        if (ls!=null)  dayAddList.addAll(ls)
        notifyDataSetChanged()
    }

}