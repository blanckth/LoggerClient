package com.android.seclogupd.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "meta_logs")
data class MetaLog(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val module: String,            // ماژول مربوطه (مثل sms, call)
    val startTimestamp: Long,     // زمان شروع لاگ‌گیری
    val deviceId: String,         // Android ID یا مشابه
    val versionCode: Int,         // نسخه اپلیکیشن
    val metaLoggedAt: Long        // زمان ثبت متا
)
