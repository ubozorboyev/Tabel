package net.city.tabel.utils

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.Drawable
import android.media.RingtoneManager
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.core.app.NotificationCompat
import net.city.tabel.ui.MainActivity
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.squareup.picasso.Picasso
import com.squareup.picasso.Target
import java.lang.Exception


class MyFirebaseMessageServis :FirebaseMessagingService(){


    override fun onNewToken(p0: String) {
        super.onNewToken(p0)
        Constant.token=p0
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {

        Log.d("FFFFF","remoteMessage ${remoteMessage.notification}")
        Log.d("FFFFF","notification?.imageUrl ${remoteMessage.notification?.imageUrl}")
        Log.d("FFFFF","notificationICON ${remoteMessage.notification?.icon}")

        var  largeIcon:Bitmap?=BitmapFactory.decodeResource(resources,net.city.tabel.R.drawable.background)

        if (remoteMessage.notification != null) {

            Handler(Looper.getMainLooper()).post(Runnable {

                Picasso.get().load(remoteMessage.notification?.imageUrl).into(object :Target{

                    override fun onPrepareLoad(placeHolderDrawable: Drawable?) {  }

                    override fun onBitmapFailed(e: Exception?, errorDrawable: Drawable?) { }

                    override fun onBitmapLoaded(bitmap: Bitmap?, from: Picasso.LoadedFrom?) {
                        largeIcon=bitmap
                    }
            }) })

            showNotification(remoteMessage.notification?.title, remoteMessage.notification?.body,largeIcon)

        }

    }


    private fun showNotification(title: String?, body: String?,image:Bitmap?) {
        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        val pendingIntent = PendingIntent.getActivity(this, 0, intent,
            PendingIntent.FLAG_ONE_SHOT)

        val soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val notificationBuilder = NotificationCompat.Builder(this)
            .setSmallIcon(net.city.tabel.R.drawable.background)
            .setLargeIcon(image)
            .setContentTitle(title)
            .setContentText(body)
            .setAutoCancel(true)
            .setSound(soundUri)
            .setContentIntent(pendingIntent)

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(0, notificationBuilder.build())
    }

}