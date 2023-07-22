package com.felippeneves.weatherforecast.screens.favorite

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.felippeneves.weatherforecast.model.Favorite
import com.felippeneves.weatherforecast.repository.FavoriteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val repository: FavoriteRepository
) : ViewModel() {
    private val _favoritesList = MutableStateFlow<List<Favorite>>(emptyList())
    val favoritesList = _favoritesList.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getFavorites().distinctUntilChanged()
                .collect { listOfFavorites ->
                    if (listOfFavorites.isEmpty()) {
                        Log.d("TAG", "Empty Favorites")
                    } else {
                        _favoritesList.value = listOfFavorites
                        Log.d("FAVORITES", "${favoritesList.value}")
                    }
                }
        }
    }

    fun insertFavorite(favorite: Favorite) =
        viewModelScope.launch { repository.insertFavorite(favorite = favorite) }

    fun updateFavorite(favorite: Favorite) =
        viewModelScope.launch { repository.updateFavorite(favorite = favorite) }

    fun deleteFavorite(favorite: Favorite) =
        viewModelScope.launch { repository.deleteFavorite(favorite = favorite) }
}