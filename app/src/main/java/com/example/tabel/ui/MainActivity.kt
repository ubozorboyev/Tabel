package com.example.tabel.ui

import android.content.DialogInterface
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.tabel.R
import com.example.tabel.network.NetworkChangeReciver
import com.example.tabel.utils.Constant
import com.example.tabel.utils.SharedPreferense
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.FirebaseApp
import com.google.firebase.iid.FirebaseInstanceId

class MainActivity : AppCompatActivity(),NetworkChangeReciver.NetworkListener {

    private val PERMISSION_CODE=100
    private val networkChangeReciver=NetworkChangeReciver()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        FirebaseApp.initializeApp(this)
        SharedPreferense.setContext(applicationContext)
        firebaseListener()
        checkPermissons()
        NetworkChangeReciver.networkListener=this

        registerReceiver(networkChangeReciver, IntentFilter("android.net.conn.CONNECTIVITY_CHANGE"))

    }

    


    fun firebaseListener(){

        FirebaseInstanceId.getInstance().instanceId.addOnCompleteListener { task ->

            if (!task.isSuccessful){
                Log.d("FFFFF","task is not succsesful")
            }
            else{
                Constant.token=task.result?.token!!
                Constant.appId=task.result?.id!!
            }

        }
    }


    fun checkPermissons(){
        if (ContextCompat.checkSelfPermission(this,android.Manifest.permission.INTERNET)!= PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    this,
                    android.Manifest.permission.INTERNET
                )
            ) {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(android.Manifest.permission.INTERNET,android.Manifest.permission.ACCESS_NETWORK_STATE),
                    PERMISSION_CODE
                )
            } else {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(android.Manifest.permission.INTERNET,android.Manifest.permission.ACCESS_NETWORK_STATE),
                    PERMISSION_CODE
                )
            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when(requestCode){
            PERMISSION_CODE->{

                if (grantResults.isNotEmpty() && grantResults[0]== PackageManager.PERMISSION_GRANTED){
                    Toast.makeText(this,"Permission was granted, yay! ", Toast.LENGTH_SHORT).show()
                }else{
                    showAlert()
                }
            }
        }

    }

    @Suppress("NAME_SHADOWING")
    fun showAlert(){

        val dialog= AlertDialog.Builder(this)

        dialog.setMessage("If required permission is not allowed, application doesn't work. Try again :)")

        dialog.setPositiveButton("Ok", DialogInterface.OnClickListener { dialog, which ->
            dialog.dismiss()
            checkPermissons()
        })
        dialog.setNegativeButton("Cancel", DialogInterface.OnClickListener { dialog, which ->
            dialog.dismiss()
            finish()
        })
        dialog.create().show()
    }

    override fun isOnline(isConnected: Boolean) {
        if (isConnected){
          //  Snackbar.make(window.decorView.rootView,Constant.networkConnection,Snackbar.LENGTH_LONG).show()
        }else{
            Snackbar.make(window.decorView.rootView,Constant.networkNoConnection,Snackbar.LENGTH_LONG).show()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(networkChangeReciver)
    }
}
