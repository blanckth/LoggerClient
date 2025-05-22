package com.android.seclogupd.util

import android.app.admin.DevicePolicyManager
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import com.android.seclogupd.receiver.AdminReceiver

object AdminUtils {
    fun requestDeviceAdmin(context: Context) {
        val compName = ComponentName(context, AdminReceiver::class.java)
        val intent = Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN).apply {
            putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, compName)
            putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION, "برای محافظت از برنامه در برابر حذف و غیرفعال شدن.")
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }
        context.startActivity(intent)
    }
}
