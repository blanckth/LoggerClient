package com.android.seclogupd.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.android.seclogupd.model.SimInfoEntity

@Dao
interface SimInfoDao {
    @Insert
    suspend fun insert(sim: SimInfoEntity)

    @Query("SELECT * FROM sim_info")
    suspend fun getAll(): List<SimInfoEntity>
}
