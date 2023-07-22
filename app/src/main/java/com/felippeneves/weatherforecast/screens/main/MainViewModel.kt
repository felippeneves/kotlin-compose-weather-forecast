package com.felippeneves.weatherforecast.screens.main

import androidx.lifecycle.ViewModel
import com.felippeneves.weatherforecast.data.wrapper_class.DataOrException
import com.felippeneves.weatherforecast.model.Weather
import com.felippeneves.weatherforecast.repository.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: WeatherRepository
) : ViewModel() {
    suspend fun getWeatherData(
        city: String,
        units: String
    ): DataOrException<Weather, Boolean, Exception> {
        return repository.getWeather(cityQuery = city, units = units)
    }
}