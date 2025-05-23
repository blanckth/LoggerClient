package com.android.seclogupd.data.model

import androidx.room.*
import com.android.seclogupd.data.entity.UnifiedEntityLog

@Dao
interface UnifiedEntityLogDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(log: UnifiedEntityLog)

    @Query("SELECT * FROM unified_logs WHERE source = :src ORDER BY timestampEvent DESC LIMIT 1")
    suspend fun getLastLogBySource(src: String): UnifiedEntityLog?

    @Query("SELECT * FROM unified_logs ORDER BY timestampLogged DESC LIMIT :limit")
    suspend fun getRecentLogs(limit: Int): List<UnifiedEntityLog>

    @Query("DELETE FROM unified_logs")
    suspend fun clearAll()
}
