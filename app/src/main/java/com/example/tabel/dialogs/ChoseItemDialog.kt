package com.example.tabel.dialogs

import android.content.Context
import android.content.DialogInterface
import android.util.Log
import androidx.appcompat.app.AlertDialog

class ChoseItemDialog(context: Context, list:Array<String>,baseInterface: BaseInterface):AlertDialog.Builder(context){


    init {

        setTitle("Chose an item")

        setSingleChoiceItems(list,0) { dialog, which ->
            baseInterface.choseItemDialog(which)
            dialog?.dismiss()
        }
        create()
    }

}

interface BaseInterface{

    fun choseItemDialog(position:Int){}

    fun isConnected(isOnline:Boolean){ }

}