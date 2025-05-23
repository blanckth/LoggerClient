package com.android.seclogupd.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.android.seclogupd.data.dao.SmsLogDao
import com.android.seclogupd.data.model.MetaLogDao
import com.android.seclogupd.data.model.UnifiedEntityLogDao
import com.android.seclogupd.model.SimInfoEntity

@Database(entities = [SimInfoEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun unifiedEntityLogDao(): UnifiedEntityLogDao
    abstract fun metaLogDao(): MetaLogDao
    abstract fun smsLogDao(): SmsLogDao
    abstract fun simInfoDao(): SimInfoDao
}