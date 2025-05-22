package com.android.seclogupd.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "sim_info")
data class SimInfoEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val carrierName: String,
    val simSlotIndex: Int,
    val number: String?,
    val countryIso: String,
    val serialNumber: String,
    val insertedTime: Long
)
