package com.felippeneves.weatherforecast.screens.settings

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.felippeneves.weatherforecast.model.Settings
import com.felippeneves.weatherforecast.repository.SettingsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val repository: SettingsRepository
) : ViewModel() {
    private val _settingsList = MutableStateFlow<List<Settings>>(emptyList())
    val settingsList = _settingsList.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getSettings().distinctUntilChanged()
                .collect { listOfSettings ->
                    if (listOfSettings.isEmpty()) {
                        Log.d("TAG", "Empty Settings")
                    } else {
                        _settingsList.value = listOfSettings
                        Log.d("FAVORITES", "${settingsList.value}")
                    }
                }
        }
    }

    fun insertSettings(settings: Settings) =
        viewModelScope.launch { repository.insertSettings(settings = settings) }

    fun updateSettings(settings: Settings) =
        viewModelScope.launch { repository.updateSettings(settings = settings) }

    fun deleteSettings(settings: Settings) =
        viewModelScope.launch { repository.deleteSettings(settings = settings) }

    fun deleteAllSettings() =
        viewModelScope.launch { repository.deleteAllSettings() }
}