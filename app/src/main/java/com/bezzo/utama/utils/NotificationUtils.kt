package com.bezzo.utama.utils

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Build
import android.os.Bundle
import androidx.core.app.NotificationCompat
import com.bezzo.utama.BuildConfig
import com.bezzo.utama.R

/**
 * Created by bezzo on 09/01/18.
 */

object NotificationUtils {

    private fun configureChannel(
        channelId: String,
        channelName: String,
        channelDescription: String?
    ): NotificationChannel? {
        var channel: NotificationChannel? = null

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val importance = NotificationManager.IMPORTANCE_HIGH
            channel = NotificationChannel(channelId, channelName, importance)

            if (channelDescription != null) {
                channel.description = "Reminders"
            }
        }

        return channel
    }

    fun createNotification(notifId: Int, title: String, messageBody: String, context: Context, notificationTarget: Class<*>,
                           resourceId: Int) {

        val data = Bundle()
        data.putString(AppConstants.FCM_MESSAGE, messageBody)

        val intent = Intent(context, notificationTarget)
        intent.putExtras(data)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)

        val requestID = System.currentTimeMillis().toInt()
        val flags = PendingIntent.FLAG_CANCEL_CURRENT
        val pIntent = PendingIntent.getActivity(context, requestID, intent, flags)

        val largeIcon = BitmapFactory.decodeResource(context.resources, resourceId)

        val mBuilder = NotificationCompat.Builder(context, BuildConfig.APPLICATION_ID)
            .setLargeIcon(largeIcon)
            .setSmallIcon(resourceId)
            .setContentTitle(title)
            .setContentIntent(pIntent)
            .setAutoCancel(true)
            .setTicker(messageBody)
            .setContentText(messageBody)
            .setDefaults(Notification.DEFAULT_ALL)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            mBuilder.priority = NotificationManager.IMPORTANCE_HIGH
        } else {
            mBuilder.priority = Notification.PRIORITY_HIGH
        }

        val mNotificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        // mId allows you to update the notification later on.

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            mNotificationManager.createNotificationChannel(
                configureChannel(
                    BuildConfig.APPLICATION_ID,
                    context.getString(R.string.app_name), null
                )!!
            )
        }

        mNotificationManager.notify(BuildConfig.APPLICATION_ID, notifId, mBuilder.build())
    }
}