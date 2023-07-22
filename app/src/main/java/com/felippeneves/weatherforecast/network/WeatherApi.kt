package com.felippeneves.weatherforecast.network

import com.felippeneves.weatherforecast.model.Weather
import com.felippeneves.weatherforecast.utils.ApiKey
import com.felippeneves.weatherforecast.utils.Constants
import retrofit2.http.GET
import retrofit2.http.Query
import javax.inject.Singleton

@Singleton
interface WeatherApi {
    @GET(value = "data/2.5/forecast/daily")
    suspend fun getWeather(
        @Query(Constants.API_PARAM_QUERY) query: String,
        @Query(Constants.API_PARAM_UNITS) units: String = Constants.API_PARAM_UNITS_VLR_DEFAULT,
        @Query(Constants.API_PARAM_APP_ID) appid: String = ApiKey.VALUE
    ): Weather
}