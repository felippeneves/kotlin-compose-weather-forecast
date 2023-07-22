package com.felippeneves.weatherforecast.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.felippeneves.weatherforecast.model.Favorite
import com.felippeneves.weatherforecast.model.Settings

@Database(
    entities = [
        Favorite::class,
        Settings::class
    ],
    version = 1,
    exportSchema = false
)
abstract class WeatherDatabase : RoomDatabase() {
    abstract fun FavoriteDao(): FavoriteDao
    abstract fun SettingsDao(): SettingsDao
}