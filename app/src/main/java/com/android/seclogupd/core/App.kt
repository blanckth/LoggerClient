package com.android.seclogupd.core

import android.app.Application

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        // اینجا بعداً می‌تونیم دیتابیس، سرویس‌ها یا ابزارهای لاگر رو init کنیم
    }
}