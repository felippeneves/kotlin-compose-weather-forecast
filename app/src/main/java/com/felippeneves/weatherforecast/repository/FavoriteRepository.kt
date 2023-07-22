package com.felippeneves.weatherforecast.repository

import com.felippeneves.weatherforecast.data.FavoriteDao
import com.felippeneves.weatherforecast.model.Favorite
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FavoriteRepository @Inject constructor(private val favoriteDao: FavoriteDao) {

    fun getFavorites(): Flow<List<Favorite>> = favoriteDao.getFavorites()

    suspend fun insertFavorite(favorite: Favorite) = favoriteDao.insertFavorite(favorite = favorite)

    suspend fun updateFavorite(favorite: Favorite) = favoriteDao.updateFavorite(favorite = favorite)

    suspend fun deleteAllFavorites() = favoriteDao.deleteAllFavorites()

    suspend fun deleteFavorite(favorite: Favorite) = favoriteDao.deleteFavorite(favorite = favorite)

    suspend fun getFavoriteById(city: String) = favoriteDao.getFavoriteById(city = city)
}