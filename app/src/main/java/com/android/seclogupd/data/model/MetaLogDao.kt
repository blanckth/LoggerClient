package com.android.seclogupd.data.model

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.android.seclogupd.data.entity.MetaLog

@Dao
interface MetaLogDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(metaLog: MetaLog)

    @Query("SELECT * FROM meta_logs WHERE module = :module LIMIT 1")
    fun getMetaForModule(module: String): MetaLog?
}
