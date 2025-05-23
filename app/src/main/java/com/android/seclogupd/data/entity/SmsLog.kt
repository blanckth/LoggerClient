package com.android.seclogupd.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "sms_logs")
data class SmsLog(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val address: String,
    val body: String,
    val type: Int, // 1: دریافتی, 2: ارسالی
    val timestamp: Long, // زمان ارسال یا دریافت پیام
    val logCreatedAt: Long // زمان ثبت لاگ در دیتابیس
)
