package com.android.seclogupd.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.android.seclogupd.model.SimInfoEntity

@Database(entities = [SimInfoEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun simInfoDao(): SimInfoDao
}