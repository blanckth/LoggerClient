package com.android.seclogupd.logger

import android.content.Context
import android.provider.Settings
import com.android.seclogupd.data.AppDatabase
import com.android.seclogupd.data.entity.MetaLog
import com.android.seclogupd.BuildConfig

class MetaLogger(private val context: Context, private val db: AppDatabase) {

    fun logStart(module: String) {
        val prefs = context.getSharedPreferences("logger_prefs", Context.MODE_PRIVATE)
        val key = "meta_logged_$module"
        if (!prefs.getBoolean(key, false)) {
            val meta = MetaLog(
                module = module,
                startTimestamp = System.currentTimeMillis(),
                deviceId = Settings.Secure.getString(context.contentResolver, Settings.Secure.ANDROID_ID),
                versionCode = BuildConfig.VERSION_CODE,
                metaLoggedAt = System.currentTimeMillis()
            )
            db.metaLogDao().insert(meta)
            prefs.edit().putBoolean(key, true).apply()
        }
    }
}
