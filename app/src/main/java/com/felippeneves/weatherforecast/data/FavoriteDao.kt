package com.felippeneves.weatherforecast.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.felippeneves.weatherforecast.model.Favorite
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteDao {
    @Query(
        "   SELECT *            " +
        "   FROM TB_FAVORITE    "
    )
    fun getFavorites(): Flow<List<Favorite>>

    @Query(
        "   SELECT *                " +
        "   FROM TB_FAVORITE        " +
        "   WHERE CITY = :city      "
    )
    suspend fun getFavoriteById(city: String): Favorite

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavorite(favorite: Favorite)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateFavorite(favorite: Favorite)

    @Query(
        "   DELETE                  " +
        "   FROM TB_FAVORITE        "
    )
    suspend fun deleteAllFavorites()

    @Delete
    suspend fun deleteFavorite(favorite: Favorite)
}