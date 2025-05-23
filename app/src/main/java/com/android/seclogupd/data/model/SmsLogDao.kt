package com.android.seclogupd.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.android.seclogupd.data.entity.SmsLog

@Dao
interface SmsLogDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(smsLog: SmsLog)

    @Query("SELECT MAX(timestamp) FROM sms_logs WHERE type = :type")
    fun getLastLoggedTimestamp(type: Int): Long?
}
