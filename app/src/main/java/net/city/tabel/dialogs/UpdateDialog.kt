package net.city.tabel.dialogs

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import net.city.tabel.R


class UpdateDialog(context: Context) :AlertDialog(context){

    private val processBar:ProgressBar
    private val textView:TextView


    init {
        val view=LayoutInflater.from(context).inflate(R.layout.update_progresbar,null,false)
        processBar=view.findViewById(R.id.progressBar)
        textView=view.findViewById(R.id.updateText)
        setView(view)
        create()
    }


    fun setTodaldownlanded(down:Long){

        val progress=down*100/processBar.max

        processBar.setProgress(down.toInt())
            Log.d("DDDDDDD","down ${down}")


        textView.setText("${progress} %")
    }


    fun setMaxProgress(maxP:Long){
        processBar.max=maxP.toInt()
        Log.d("DDDDDDD","maxxxxx ${maxP}")
        Log.d("DDDDDDD","maxxxxx processBar ${processBar.max}")
    }
}