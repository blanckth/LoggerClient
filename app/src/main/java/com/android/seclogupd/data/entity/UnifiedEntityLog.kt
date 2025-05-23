package com.android.seclogupd.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "unified_logs")
data class UnifiedEntityLog(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val source: String,                // sms, call, file, etc.
    val subtype: String?,             // inbox, sent, deleted, etc.
    val title: String?,               // شماره، نام، یا فایل
    val content: String?,             // متن، محتوا یا جزئیات
    val timestampEvent: Long,         // زمان واقعی وقوع رویداد
    val timestampLogged: Long,        // زمان ثبت در سیستم
    val extra: String?                // JSON برای اطلاعات اضافه (simSlot, status...)
)
