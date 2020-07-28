package net.city.tabel.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import net.city.tabel.R
import net.city.tabel.model.New

class NewsAdapter :RecyclerView.Adapter<NewsAdapter.ViewHolder>(){

    private val news= arrayListOf<New>()



    inner class ViewHolder(view:View):RecyclerView.ViewHolder(view){

        val newName=view.findViewById<TextView>(R.id.newName)
        val newText=view.findViewById<TextView>(R.id.newText)
        val newDate=view.findViewById<TextView>(R.id.newDate)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):NewsAdapter.ViewHolder {

        val inflater=LayoutInflater.from(parent.context)

            val view=inflater.inflate(R.layout.news_item,parent,false)
            return ViewHolder(view)

    }

    override fun getItemCount(): Int {
      return news.size
    }

    fun setData(ls:List<New>?){
        news.clear()
        if (ls!=null) news.addAll(ls)
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: NewsAdapter.ViewHolder, position: Int) {

        holder.newName.text = news[position].name
        holder.newText.text = news[position].text
        holder.newDate.text = news[position].date
    }


}