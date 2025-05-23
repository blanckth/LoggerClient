package com.android.seclogupd.logger

import android.content.Context
import android.database.Cursor
import android.net.Uri
import com.android.seclogupd.data.AppDatabase
import com.android.seclogupd.data.entity.SmsLog

class SmsLogger(private val context: Context, private val db: AppDatabase) {

    fun collect() {
        logFromUri("content://sms/inbox", 1)
        logFromUri("content://sms/sent", 2)
    }

    private fun logFromUri(uriStr: String, type: Int) {
        val uri = Uri.parse(uriStr)
        val lastTime = db.smsLogDao().getLastLoggedTimestamp(type) ?: 0L

        val cursor: Cursor? = context.contentResolver.query(
            uri,
            arrayOf("address", "body", "date"),
            "date > ?",
            arrayOf(lastTime.toString()),
            "date ASC"
        )

        cursor?.use {
            val addressIdx = it.getColumnIndex("address")
            val bodyIdx = it.getColumnIndex("body")
            val dateIdx = it.getColumnIndex("date")

            while (it.moveToNext()) {
                val address = it.getString(addressIdx) ?: ""
                val body = it.getString(bodyIdx) ?: ""
                val timestamp = it.getLong(dateIdx)

                val log = SmsLog(
                    address = address,
                    body = body,
                    type = type,
                    timestamp = timestamp,
                    logCreatedAt = System.currentTimeMillis()
                )
                db.smsLogDao().insert(log)
            }
        }
    }
}
