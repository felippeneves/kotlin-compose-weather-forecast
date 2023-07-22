package com.felippeneves.weatherforecast.di

import android.content.Context
import androidx.room.Room
import com.felippeneves.weatherforecast.data.FavoriteDao
import com.felippeneves.weatherforecast.data.SettingsDao
import com.felippeneves.weatherforecast.data.WeatherDatabase
import com.felippeneves.weatherforecast.network.WeatherApi
import com.felippeneves.weatherforecast.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    //region Database

    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context): WeatherDatabase =
        Room.databaseBuilder(
            context = context,
            WeatherDatabase::class.java,
            name = "weather_database"
        )
            .fallbackToDestructiveMigration()
            .build()

    //endregion

    //region DAO

    @Provides
    @Singleton
    fun provideSettingsDao(weatherDatabase: WeatherDatabase): SettingsDao =
        weatherDatabase.SettingsDao()

    @Provides
    @Singleton
    fun provideFavoriteDao(weatherDatabase: WeatherDatabase): FavoriteDao =
        weatherDatabase.FavoriteDao()

    //endregion

    //region API

    @Provides
    @Singleton
    fun provideOpenWeatherApi(): WeatherApi {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(WeatherApi::class.java)
    }

    //endregion
}