package com.felippeneves.weatherforecast.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.felippeneves.weatherforecast.model.Settings
import kotlinx.coroutines.flow.Flow

@Dao
interface SettingsDao {
    @Query(
        "   SELECT *            " +
        "   FROM TB_SETTINGS    "
    )
    fun getSettings(): Flow<List<Settings>>

    @Query(
        "   SELECT *                " +
        "   FROM TB_SETTINGS        " +
        "   WHERE UNIT = :unit      "
    )
    suspend fun getSettingsById(unit: String): Settings

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSettings(settings: Settings)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateSettings(settings: Settings)

    @Query(
        "   DELETE                  " +
        "   FROM TB_SETTINGS        "
    )
    suspend fun deleteAllSettings()

    @Delete
    suspend fun deleteSettings(settings: Settings)
}