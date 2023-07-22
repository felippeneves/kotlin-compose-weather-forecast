package com.felippeneves.weatherforecast.repository

import com.felippeneves.weatherforecast.data.wrapper_class.DataOrException
import com.felippeneves.weatherforecast.model.Weather
import com.felippeneves.weatherforecast.network.WeatherApi
import java.lang.Exception
import javax.inject.Inject

class WeatherRepository @Inject constructor(private val api: WeatherApi) {
    suspend fun getWeather(cityQuery: String, units: String): DataOrException<Weather, Boolean, Exception> {
        val response = try {
            api.getWeather(query = cityQuery, units = units)
        } catch (e: Exception) {
            return DataOrException(e = e)
        }

        return DataOrException(data = response)
    }
}