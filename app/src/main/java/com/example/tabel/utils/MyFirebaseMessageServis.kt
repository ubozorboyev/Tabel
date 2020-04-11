package com.example.tabel.utils

import android.R
import android.app.Notification
import android.app.Notification.DEFAULT_SOUND
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationCompat.DEFAULT_SOUND
import com.example.tabel.MainActivity
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage


class MyFirebaseMessageServis :FirebaseMessagingService(){


    override fun onNewToken(p0: String) {
        super.onNewToken(p0)
        Constant
        Log.d("FFFFF","newToken $p0")
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {

        Log.d("FFFFF","remoteMessage $remoteMessage")

        if (remoteMessage.notification != null) {
            showNotification(remoteMessage.notification?.title, remoteMessage.notification?.body)
        }

    }
    private fun showNotification(title: String?, body: String?) {
        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        val pendingIntent = PendingIntent.getActivity(this, 0, intent,
            PendingIntent.FLAG_ONE_SHOT)

        val soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val notificationBuilder = NotificationCompat.Builder(this)
            .setSmallIcon(R.mipmap.sym_def_app_icon)
            .setContentTitle(title)
            .setContentText(body)
            .setAutoCancel(true)
            .setSound(soundUri)
            .setContentIntent(pendingIntent)

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(0, notificationBuilder.build())
    }


    // offline token dKdviMBYRcu5fXF2bEcBJt:APA91bEVULzHFFnsQhm_2il0u-eWfL-ICXnTmEFkm0JNjskDtu2y41RP0iS0_EL4FWm61re35Ih6EU2fMPrUfomg8kTTad1W9oLuYkkIWYGxzeMwMG1ah5JeLdXYU2sf-Nkf-RjwJu3i

}