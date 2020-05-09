package com.example.tabel.ui

import android.app.Activity
import android.content.DialogInterface
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.tabel.R
import com.example.tabel.dialogs.UpdateDialog
import com.example.tabel.network.NetworkChangeReciver
import com.example.tabel.utils.Constant
import com.example.tabel.utils.SharedPreferense
import com.google.android.material.snackbar.Snackbar
import com.google.android.play.core.appupdate.AppUpdateInfo
import com.google.android.play.core.appupdate.AppUpdateManager
import com.google.android.play.core.appupdate.AppUpdateManagerFactory
import com.google.android.play.core.install.InstallStateUpdatedListener
import com.google.android.play.core.install.model.AppUpdateType
import com.google.android.play.core.install.model.InstallStatus
import com.google.android.play.core.install.model.UpdateAvailability
import com.google.firebase.FirebaseApp
import com.google.firebase.iid.FirebaseInstanceId

class MainActivity : AppCompatActivity(),NetworkChangeReciver.NetworkListener {

    private val PERMISSION_CODE=100
    private val UPDATE_REQUESTCODE=101
    private val networkChangeReciver=NetworkChangeReciver()
    private val appUpdateManager by lazy{ AppUpdateManagerFactory.create(this)}
    private lateinit var listener:InstallStateUpdatedListener


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        FirebaseApp.initializeApp(this)
        SharedPreferense.setContext(applicationContext)
        firebaseListener()
        checkPermissons()
        updateApp()
        NetworkChangeReciver.networkListener=this

        registerReceiver(networkChangeReciver, IntentFilter("android.net.conn.CONNECTIVITY_CHANGE"))

    }

    override fun onResume() {
        super.onResume()

        appUpdateManager
            .appUpdateInfo
            .addOnSuccessListener { appUpdateInfo ->
                if (appUpdateInfo.installStatus() == InstallStatus.DOWNLOADED) {
                    popupSnackbarForCompleteUpdate()
                }
            }
    }

    fun popupSnackbarForCompleteUpdate() {
        Snackbar.make(
            window.decorView.rootView,
            "An update has just been downloaded.",
            Snackbar.LENGTH_INDEFINITE
        ).apply {
            setAction("RESTART") { appUpdateManager.completeUpdate() }
            setActionTextColor(resources.getColor(R.color.colorPrimaryDark))
            show()
        }
    }
    fun updateApp(){

        val appUpdateInfoTask = appUpdateManager.appUpdateInfo

         appUpdateInfoTask.addOnSuccessListener { appUpdateInfo ->

            if (appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE

                && appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.IMMEDIATE)
            ) {
                appUpdateManager?.startUpdateFlowForResult(appUpdateInfo,AppUpdateType.IMMEDIATE,this,UPDATE_REQUESTCODE)
            }
        }

         listener =InstallStateUpdatedListener { state ->
             val dialog=UpdateDialog(this)

            if (state.installStatus() == InstallStatus.DOWNLOADING) {
                val bytesDownloaded = state.bytesDownloaded()
                val totalBytesToDownload = state.totalBytesToDownload()

                dialog.setTodaldownlanded(totalBytesToDownload)
                dialog.setMaxProgress(bytesDownloaded)
                dialog.show()
                if (bytesDownloaded==totalBytesToDownload) dialog.dismiss()
            }

             if (state.installStatus()==InstallStatus.DOWNLOADED){
                 dialog.dismiss()
             }

        }/*

        appUpdateManager.appUpdateInfo.addOnSuccessListener {appUpdateInfo->
            if (appUpdateInfo.installStatus()==InstallStatus.DOWNLOADED){
                popupSnackbarForCompleteUpdate()
            }

        }*/

        appUpdateManager.registerListener(listener)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        if (requestCode==UPDATE_REQUESTCODE){
            if (resultCode!= Activity.RESULT_OK){
                updateApp()
            }
        }

        super.onActivityResult(requestCode, resultCode, data)
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
        try {
            appUpdateManager.unregisterListener(listener)
        }catch (e:Exception){
            e.printStackTrace()
        }
        unregisterReceiver(networkChangeReciver)
        super.onDestroy()
    }
}

