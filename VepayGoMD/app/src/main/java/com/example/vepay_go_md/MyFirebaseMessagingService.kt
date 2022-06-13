package com.example.vepay_go_md

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import android.widget.RemoteViews
import android.widget.Toast
import androidx.core.app.NotificationCompat
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.EnhancedIntentService
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.RemoteMessage
import okhttp3.internal.notify
import java.util.HashMap
import kotlin.math.log

const val channel_id = "notification channel"
const val channel_name="com.example.vepay_go_md"
private lateinit var store: FirebaseFirestore

class MyFirebaseMessagingService : FirebaseMessagingService() {


   fun generateNotification(title: String, message: String){
        val intent = Intent(this, DashboardActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)

        val pendingIntent = PendingIntent.getActivity(this, 0,intent,PendingIntent.FLAG_ONE_SHOT)

        var builder: NotificationCompat.Builder = NotificationCompat.Builder(applicationContext,
            channel_id)
            .setSmallIcon(R.drawable.circle_bg)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)
            .setOnlyAlertOnce(true)

        builder = builder.setContent(getRemoteView(title,message))

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val notificationChannel = NotificationChannel(channel_id, channel_name, NotificationManager.IMPORTANCE_HIGH)
            notificationManager.createNotificationChannel(notificationChannel)
        }
        notificationManager.notify(0,builder.build())
    }

   fun getRemoteView(title: String,message: String): RemoteViews{
       val remoteviews = RemoteViews("com.example.vepay_go_md",R.layout.notification)
       remoteviews.setTextViewText(R.id.title,title)
       remoteviews.setTextViewText(R.id.message,message)
       remoteviews.setImageViewResource(R.id.logo,R.drawable.circle_bg)
       return remoteviews

   }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        if (remoteMessage.getNotification()!=null){
            generateNotification(remoteMessage.notification!!.title!!,remoteMessage.notification!!.body!!)
        }
    }





    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Log.d(TAG, "Refreshed token: $token")

       sendRegistrationToServer(token)
    }

    private fun sendRegistrationToServer(token: String) {


    }


}