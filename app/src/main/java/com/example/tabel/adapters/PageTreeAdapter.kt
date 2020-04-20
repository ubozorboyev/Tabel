package com.example.tabel.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.tabel.R
import com.example.tabel.model.Worker
import com.squareup.picasso.Picasso

class PageTreeAdapter :RecyclerView.Adapter<PageTreeAdapter.ViewHolder>(){

     private val workerList= arrayListOf<Worker>()


    inner class ViewHolder(view:View):RecyclerView.ViewHolder(view){

        val id=view.findViewById<TextView>(R.id.workerId)
        val name=view.findViewById<TextView>(R.id.workerName)
        val imageView=view.findViewById<ImageView>(R.id.workerImage)
        val workerPosition=view.findViewById<TextView>(R.id.workerPosition)
        val phonoNumber=view.findViewById<TextView>(R.id.workerNumber)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater=LayoutInflater.from(parent.context)
        val view=inflater.inflate(R.layout.worker_item,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return workerList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val worker=workerList[position]
        holder.id.text=worker.i.toString()
        holder.name.text=worker.workername
        holder.phonoNumber.text=worker.workertel
        holder.workerPosition.text=worker.workerposition
        Picasso.get().load(worker.workerimg).placeholder(R.drawable.ic_person_black_24dp)
            .error(R.drawable.ic_person_black_24dp).into(holder.imageView)

    }

    fun setData(ls:List<Worker>?){

        workerList.clear()

        if (ls!=null) workerList.addAll(ls)

        notifyDataSetChanged()
    }


}