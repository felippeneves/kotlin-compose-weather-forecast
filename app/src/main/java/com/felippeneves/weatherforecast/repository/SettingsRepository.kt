package com.felippeneves.weatherforecast.repository

import com.felippeneves.weatherforecast.data.SettingsDao
import com.felippeneves.weatherforecast.model.Settings
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SettingsRepository @Inject constructor(private val settingsDao: SettingsDao) {

    fun getSettings(): Flow<List<Settings>> = settingsDao.getSettings()

    suspend fun insertSettings(settings: Settings) = settingsDao.insertSettings(settings = settings)

    suspend fun updateSettings(settings: Settings) = settingsDao.updateSettings(settings = settings)

    suspend fun deleteAllSettings() = settingsDao.deleteAllSettings()

    suspend fun deleteSettings(settings: Settings) = settingsDao.deleteSettings(settings = settings)

    suspend fun getSettingsById(unit: String) = settingsDao.getSettingsById(unit = unit)
}