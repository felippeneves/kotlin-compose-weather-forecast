package com.felippeneves.weatherforecast.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "TB_SETTINGS")
data class Settings(
    @PrimaryKey
    @ColumnInfo(name = "UNIT")
    val unit: String
)
