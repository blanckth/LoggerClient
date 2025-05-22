package com.android.seclogupd.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.android.seclogupd.service.LoggerService

class BootReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        if (Intent.ACTION_BOOT_COMPLETED == intent.action || Intent.ACTION_LOCKED_BOOT_COMPLETED == intent.action) {
            Log.d("BootReceiver", "Device booted. Starting LoggerService...")
            val serviceIntent = Intent(context, LoggerService::class.java)
            context.startForegroundService(serviceIntent)
        }
    }
}
