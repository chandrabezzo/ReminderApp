package com.bezzo.utama.services

import com.bezzo.utama.R
import com.bezzo.utama.ui.MainActivity
import com.bezzo.utama.utils.AppConstants
import com.bezzo.utama.utils.NotificationUtils
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.orhanobut.hawk.Hawk

class FCMService: FirebaseMessagingService() {

    override fun onNewToken(token: String?) {
       Hawk.put(AppConstants.FCM_TOKEN, token)
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage?) {
        super.onMessageReceived(remoteMessage)

        val data = remoteMessage?.data

        NotificationUtils.createNotification(System.currentTimeMillis().toInt(), data?.get(AppConstants.TITLE) ?: "FCM Notification",
            data?.get(AppConstants.MESSAGE) ?: "Send From FCM", applicationContext, MainActivity::class.java,
            R.drawable.ic_notifications_black_24dp)
    }
}