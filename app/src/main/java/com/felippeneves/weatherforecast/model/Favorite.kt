package com.felippeneves.weatherforecast.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "TB_FAVORITE")
data class Favorite(
    @PrimaryKey
    @ColumnInfo(name = "CITY")
    val city: String,

    @ColumnInfo(name = "COUNTRY")
    val country: String,
)
