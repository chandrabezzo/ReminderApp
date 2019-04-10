package com.bezzo.utama.services

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.bezzo.utama.ui.MainActivity
import com.bezzo.utama.utils.NotificationUtils
import com.bezzo.utama.R

class WorkManagerService(val context: Context, val workerParams: WorkerParameters): Worker(context, workerParams) {
    override fun doWork(): Result {
        return try {
            val title = "Local Notification"
            val message = "One Time Worker, when network connected"

            NotificationUtils.createNotification(System.currentTimeMillis().toInt(), title, message, context,
                MainActivity::class.java, R.drawable.ic_notifications_black_24dp)

            Result.success()
        }
        catch (throwable : Throwable){
            Result.retry()
        }
    }
}

class PeriodicService(val context: Context, val workerParams: WorkerParameters): Worker(context, workerParams) {
    override fun doWork(): Result {
        return try {
            val title = "Local Notification"
            val message = "Periodic Worker"

            NotificationUtils.createNotification(System.currentTimeMillis().toInt(), title, message, context,
                MainActivity::class.java, R.drawable.ic_notifications_black_24dp)

            Result.success()
        }
        catch (throwable : Throwable){
            Result.retry()
        }
    }
}